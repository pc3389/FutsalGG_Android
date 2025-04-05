package com.futsalgg.app.presentation.user.createuser

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.usecase.CreateUserUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.user.createuser.components.isValidBirthday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _createUserState = MutableStateFlow(CreateUserState())
    val createUserState: StateFlow<CreateUserState> = _createUserState.asStateFlow()

    internal fun onNicknameChange(newValue: String) {
        _createUserState.value = _createUserState.value.copy(
            nickname = newValue,
            nicknameState = EditTextState.Initial
        )
    }

    internal fun onBirthdayChange(value: String) {
        _createUserState.value = _createUserState.value.copy(
            birthday = value,
            birthdayState = EditTextState.Initial
        )
    }

    internal fun onBirthdaySelect(date: LocalDate) {
        _createUserState.value = _createUserState.value.copy(
            birthday = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        )
        validateBirthday()
    }

    internal fun onCalendarClick() {
        _createUserState.value = _createUserState.value.copy(
            showCalendarSheet = true
        )
    }

    internal fun onDismissCalendar() {
        _createUserState.value = _createUserState.value.copy(
            showCalendarSheet = false
        )
        validateBirthday()
    }

    internal fun onGenderChange(gender: Gender) {
        _createUserState.value = _createUserState.value.copy(
            gender = gender
        )
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _createUserState.value = _createUserState.value.copy(
            croppedProfileImage = bitmap
        )
    }

    internal fun toggleNotification() {
        _createUserState.value = _createUserState.value.copy(
            notificationChecked = !_createUserState.value.notificationChecked
        )
    }

    fun validateBirthday() {
        _uiState.value = UiState.Loading
        val state = _createUserState.value
        _createUserState.value = state.copy(
            birthdayState = if (state.birthday.isEmpty()) {
                EditTextState.Initial
            } else if (!isValidBirthday(state.birthday)) {
                EditTextState.ErrorCannotUse
            } else {
                EditTextState.Default
            }
        )
        _uiState.value = UiState.Success
    }

    internal fun checkNicknameDuplication() {
        val currentNickname = _createUserState.value.nickname

        if (!currentNickname.matches(Regex("^[ㄱ-힣]+$"))) {
            _createUserState.value = _createUserState.value.copy(
                nicknameState = EditTextState.ErrorCannotUse
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            
            try {
                val result = createUserUseCase.isNicknameUnique(currentNickname)
                result.fold(
                    onSuccess = { isUnique ->
                        _createUserState.value = _createUserState.value.copy(
                            nicknameState = if (isUnique) {
                                EditTextState.Available
                            } else {
                                EditTextState.ErrorAlreadyExisting
                            }
                        )
                        _uiState.value = UiState.Success
                    },
                    onFailure = { error ->
                        Log.e(
                            "CreateUserViewModel",
                            "Nickname check 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                        _createUserState.value = _createUserState.value.copy(
                            nicknameState = EditTextState.ErrorAlreadyExisting
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateUserViewModel", "Exception during nickname check", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _createUserState.value = _createUserState.value.copy(
                    nicknameState = EditTextState.ErrorAlreadyExisting
                )
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
                    _createUserState.value.birthday, DateTimeFormatter.ofPattern("yyyyMMdd")
                ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateUserViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                val result = createUserUseCase.createUser(
                    accessToken = accessToken,
                    nickname = _createUserState.value.nickname,
                    birthDate = formattedBirthDate,
                    gender = _createUserState.value.gender,
                    agreement = true,
                    notification = _createUserState.value.notificationChecked
                )
                result.fold(
                    onSuccess = {
                        _uiState.value = UiState.Success
                        onSuccess()
                    },
                    onFailure = { error ->
                        Log.e(
                            "CreateUserViewModel",
                            "Signup 에러: ${error.message}",
                            error
                        )
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                        _createUserState.value = _createUserState.value.copy(
                            errorMessage = error.message
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("CreateUserViewModel", "Exception during Signup", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
                _createUserState.value = _createUserState.value.copy(
                    errorMessage = e.message
                )
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

            val result = createUserUseCase.uploadProfileImage(accessToken, file)
            result.fold(
                onSuccess = { response ->
                    _createUserState.value = _createUserState.value.copy(
                        profileImageUrl = response.url
                    )
                    _uiState.value = UiState.Success
                },
                onFailure = { throwable ->
                    logError("프로필 이미지 업로드 오류: ${throwable.message}")
                    _uiState.value = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                    _createUserState.value = _createUserState.value.copy(
                        errorMessage = throwable.message
                    )
                }
            )
        }
    }

    private fun logError(message: String) {
        Log.e("CreateUserViewModel", message)
    }
}