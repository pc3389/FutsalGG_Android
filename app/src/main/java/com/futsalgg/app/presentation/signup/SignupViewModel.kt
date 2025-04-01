package com.futsalgg.app.presentation.signup

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.core.token.ITokenManager
import com.futsalgg.app.data.model.request.UpdateProfileRequest
import com.futsalgg.app.data.model.response.ProfilePresignedUrlResponse
import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.domain.usecase.SignupUseCase
import com.futsalgg.app.presentation.signup.components.isValidBirthday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _nickname = MutableStateFlow("")
    val nickname: StateFlow<String> = _nickname.asStateFlow()

    val isNicknameValid: StateFlow<Boolean> = nickname.map { it.length >= 3 }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    // EditText의 상태를 관리하는 Flow (중복 체크 결과 포함)
    private val _nicknameState = MutableStateFlow(EditTextState.Default)
    val nicknameState: StateFlow<EditTextState> = _nicknameState.asStateFlow()

    // 로딩 상태 추가
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // 프로필 업데이트 응답 저장용 상태 (필요시)
    private val _profileUpdateResponse = MutableStateFlow<String?>(null)
    val profileUpdateResponse: StateFlow<String?> = _profileUpdateResponse

    private val _birthday = MutableStateFlow("")
    val birthday: StateFlow<String> = _birthday.asStateFlow()

    private val _birthdayState = MutableStateFlow(EditTextState.Default)
    val birthdayState: StateFlow<EditTextState> = _birthdayState.asStateFlow()

    private val _showCalendarSheet = MutableStateFlow(false)
    val showCalendarSheet: StateFlow<Boolean> = _showCalendarSheet.asStateFlow()

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender.asStateFlow()

    private val _croppedProfileImage = MutableStateFlow<Bitmap?>(null)
    val croppedProfileImage: StateFlow<Bitmap?> = _croppedProfileImage.asStateFlow()

    // TODO 사용되지 않으면 삭제
    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl.asStateFlow()

    private val _notificationChecked = MutableStateFlow(false)
    val notificationChecked: StateFlow<Boolean> = _notificationChecked.asStateFlow()

    internal fun onNicknameChange(newValue: String) {
        _nickname.value = newValue
        _nicknameState.value = EditTextState.Default
    }

    internal fun onBirthdayChange(value: String) {
        _birthday.value = value
        _birthdayState.value = EditTextState.Default
    }

    internal fun onBirthdaySelect(date: LocalDate) {
        _birthday.value = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
    }

    internal fun onCalendarClick() {
        _showCalendarSheet.value = true
    }

    internal fun onDismissCalendar() {
        _showCalendarSheet.value = false
        validateBirthday()
    }

    internal fun onGenderChange(gender: Gender) {
        _gender.value = gender
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _croppedProfileImage.value = bitmap
    }

    internal fun toggleNotification() {
        _notificationChecked.value = !_notificationChecked.value
    }

    fun validateBirthday() {
        if (!isValidBirthday(_birthday.value)) {
            _birthdayState.value = EditTextState.ErrorCannotUse
        } else {
            _birthdayState.value = EditTextState.Available
        }
    }

    /**
     * 닉네임 중복 체크 API 호출 함수
     * - 한글 이외의 문자가 포함된 경우 API 호출 없이 ErrorCannotUse 상태를 설정합니다.
     * - API 호출 결과에 따라 유니크하면 Available, 중복이면 ErrorAlreadyExisting로 업데이트합니다.
     */
    internal fun checkNicknameDuplication() {
        val currentNickname = _nickname.value

        // 한글만 사용해야 함 (정규식: 시작부터 끝까지 모두 한글)
        if (!currentNickname.matches(Regex("^[가-힣]+$"))) {
            _nicknameState.value = EditTextState.ErrorCannotUse
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = signupUseCase.isNicknameUnique(currentNickname)
                result.onSuccess { isUnique ->
                    _nicknameState.value = if (isUnique) {
                        EditTextState.Available
                    } else {
                        EditTextState.ErrorAlreadyExisting
                    }
                }.onFailure {
                    Log.e(
                        "SignupViewModel",
                        "Nickname check 에러: ${it.message}",
                        it
                    )
                    _nicknameState.value = EditTextState.ErrorAlreadyExisting
                }
            } catch (e: Exception) {
                Log.e("SignupViewModel", "Exception during nickname check", e)
                _nicknameState.value = EditTextState.ErrorAlreadyExisting
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * 신규 사용자 정보를 등록하는 API 호출 함수
     */
    internal fun createUser(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // 내부 날짜 형식 "yyyyMMdd"를 "yyyy-MM-dd"로 변환
                val formattedBirthDate = LocalDate.parse(
                    _birthday.value, DateTimeFormatter.ofPattern("yyyyMMdd")
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                // 토큰 매니저에서 AccessToken 가져오기
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("SignupViewModel", "엑세스 토큰이 존재하지 않습니다")
                    return@launch
                }

                val result = signupUseCase.createUser(
                    accessToken = accessToken,
                    nickname = _nickname.value,
                    birthDate = formattedBirthDate,
                    gender = _gender.value,
                    agreement = true,
                    notification = _notificationChecked.value
                )
                result.onSuccess {
                    onSuccess()
                }.onFailure {
                    Log.e(
                        "SignupViewModel",
                        "Signup 에러: ${it.message}",
                        it
                    )
                }
            } catch (e: Exception) {
                Log.e("SignupViewModel", "Exception during Signup", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * 프로필 이미지 업로드 전체 흐름을 실행합니다.
     * 1. presigned URL 획득
     * 2. 파일 업로드
     * 3. 프로필 업데이트 API 호출
     */
    fun uploadProfileImage(file: File) {
        viewModelScope.launch {
            _isLoading.value = true

            // 토큰 매니저에서 AccessToken을 가져옵니다.
            val accessToken = tokenManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                logError("AccessToken이 존재하지 않습니다.")
                return@launch
            }

            // Repository의 업로드 플로우 실행
            val result = signupUseCase.uploadProfileImage(accessToken, file)
            result.onSuccess { response ->
                // response.url 또는 response.uri를 활용해 UI 상태 업데이트
                _profileImageUrl.value = response.url
            }.onFailure { throwable ->
                logError("프로필 이미지 업로드 오류: ${throwable.message}")
            }
            _isLoading.value = false
        }
    }

    // 에러 처리 (로그 출력 또는 SharedFlow 사용 가능)
    // 여기서는 단순 로그 출력 예시로 남김
    private fun logError(message: String) {
        Log.e("SignupViewModel", message)
    }
}