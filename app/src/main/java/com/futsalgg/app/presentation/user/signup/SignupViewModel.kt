package com.futsalgg.app.presentation.user.signup

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.usecase.SignupUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.user.signup.components.isValidBirthday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
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

    private val _nicknameState = MutableStateFlow(EditTextState.Default)
    val nicknameState: StateFlow<EditTextState> = _nicknameState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

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

    internal fun checkNicknameDuplication() {
        val currentNickname = _nickname.value

        if (!currentNickname.matches(Regex("^[가-힣]+$"))) {
            _nicknameState.value = EditTextState.ErrorCannotUse
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val result = signupUseCase.isNicknameUnique(currentNickname)
                result.fold(
                    onSuccess = { isUnique ->
                        _nicknameState.value = if (isUnique) {
                            EditTextState.Available
                        } else {
                            EditTextState.ErrorAlreadyExisting
                        }
                        _uiState.value = UiState.Success
                    },
                    onFailure = { error ->
                        Log.e(
                            "SignupViewModel",
                            "Nickname check 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                        _nicknameState.value = EditTextState.ErrorAlreadyExisting
                    }
                )
            } catch (e: Exception) {
                Log.e("SignupViewModel", "Exception during nickname check", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _nicknameState.value = EditTextState.ErrorAlreadyExisting
            }
        }
    }

    internal fun createUser(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val formattedBirthDate = LocalDate.parse(
                    _birthday.value, DateTimeFormatter.ofPattern("yyyyMMdd")
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("SignupViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
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
                result.fold(
                    onSuccess = {
                        _uiState.value = UiState.Success
                        onSuccess()
                    },
                    onFailure = { error ->
                        Log.e(
                            "SignupViewModel",
                            "Signup 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("SignupViewModel", "Exception during Signup", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    fun uploadProfileImage(file: File) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val accessToken = tokenManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                logError("AccessToken이 존재하지 않습니다.")
                _uiState.value = UiState.Error(UiError.AuthError("AccessToken이 존재하지 않습니다"))
                return@launch
            }

            val result = signupUseCase.uploadProfileImage(accessToken, file)
            result.fold(
                onSuccess = { response ->
                    _profileImageUrl.value = response.url
                    _uiState.value = UiState.Success
                },
                onFailure = { throwable ->
                    logError("프로필 이미지 업로드 오류: ${throwable.message}")
                    _uiState.value = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                }
            )
        }
    }

    private fun logError(message: String) {
        Log.e("SignupViewModel", message)
    }
}