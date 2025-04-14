package com.futsalgg.app.presentation.user.updateprofile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.usecase.UpdateProfileUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.user.util.NicknameChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val nicknameChecker: NicknameChecker,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow(UpdateProfileState.Initial)
    val profileState: StateFlow<UpdateProfileState> = _profileState.asStateFlow()

    fun updateName(newName: String) {
        _profileState.value = _profileState.value.copy(nickname = newName)
    }

    fun updateSquadNumber(newSquadNumber: String?) {
        _profileState.value = _profileState.value.copy(
            squadNumber = if (newSquadNumber.isNullOrEmpty()) null else newSquadNumber.toInt()
        )
    }

    internal fun setCroppedImage(bitmap: Bitmap) {
        _profileState.value = _profileState.value.copy(
            croppedProfileImage = bitmap
        )
    }

    // CreateUserViewModel에서
    internal fun checkNicknameDuplication() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            nicknameChecker.checkNickname(
                nickname = _profileState.value.nickname,
                onStateUpdate = { newState ->
                    _profileState.value = _profileState.value.copy(
                        nicknameState = newState
                    )
                },
                onUiStateUpdate = { newState ->
                    _uiState.value = newState
                }
            )
        }
    }

    fun updateProfile(
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateUserViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                updateProfileUseCase(
                    accessToken,
                    _profileState.value.nickname,
                    _profileState.value.squadNumber
                )
                    .onSuccess { user ->
                        _uiState.value = UiState.Success
                        _profileState.value = UpdateProfileState(
                            email = user.email,
                            nickname = user.name,
                            squadNumber = user.squadNumber,
                            profileUrl = user.profileUrl,
                            notification = user.notification,
                            createdTime = user.createdTime
                        )
                        onSuccess()
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                Log.e("CreateUserViewModel", "Exception during Signup", e)
                _uiState.value = UiState.Error(UiError.UnknownError("알 수 없는 오류가 발생했습니다."))
            }
        }
    }
}