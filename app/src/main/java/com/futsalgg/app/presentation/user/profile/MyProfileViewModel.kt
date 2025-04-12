package com.futsalgg.app.presentation.user.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.model.User
import com.futsalgg.app.domain.user.usecase.GetMyProfileUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val getMyProfileUseCase: GetMyProfileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileUserState())
    val profileState: StateFlow<ProfileUserState> = _profileState.asStateFlow()

    fun getProfile(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            getMyProfileUseCase(accessToken)
                .onSuccess { user ->
                    _profileState.value = ProfileUserState(
                        email = user.email,
                        name = user.name,
                        squadNumber = user.squadNumber,
                        notification = user.notification,
                        profileUrl = user.profileUrl
                    )
                }
                .onFailure { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                }
        }
    }
} 