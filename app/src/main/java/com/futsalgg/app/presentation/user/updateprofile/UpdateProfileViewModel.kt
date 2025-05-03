package com.futsalgg.app.presentation.user.updateprofile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.usecase.UpdateProfileUseCase
import com.futsalgg.app.domain.user.usecase.UploadUserProfilePictureUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.user.util.NicknameChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val nicknameChecker: NicknameChecker,
    private val tokenManager: ITokenManager,
    private val uploadUserProfilePictureUseCase: UploadUserProfilePictureUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow(UpdateProfileState.Initial)
    val profileState: StateFlow<UpdateProfileState> = _profileState.asStateFlow()

    private val accessToken = tokenManager.getAccessToken() ?: ""
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
                                ?: UiError.UnknownError("[updateProfile] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("[updateProfile] 알 수 없는 오류가 발생했습니다: ${e.message}"))
            }
        }
    }

    fun uploadProfileImage(file: File, imageUploadSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = withContext(Dispatchers.IO) {
                uploadUserProfilePictureUseCase.uploadProfileImage(accessToken, file)
            }
            result.fold(
                onSuccess = { response ->
                    _profileState.value = _profileState.value.copy(
                        profileUrl = response.url
                    )
                    imageUploadSuccess()
                    _uiState.value = UiState.Success
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("[uploadProfileImage] 알 수 없는 오류가 발생했습니다: ${error.message}")
                    )
                }
            )
        }
    }
}