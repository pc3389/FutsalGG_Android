package com.futsalgg.app.presentation.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.user.usecase.GetMyProfileForSettingUseCase
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
class SettingViewModel @Inject constructor(
    private val getMyProfileForSettingUseCase: GetMyProfileForSettingUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _settingState = MutableStateFlow(SettingState())
    val settingState: StateFlow<SettingState> = _settingState.asStateFlow()

    init {
        getProfile()
    }
    fun getProfile() {
        viewModelScope.launch {

            val accessToken = tokenManager.getAccessToken()
            if (accessToken.isNullOrEmpty()) {
                Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                return@launch
            }

            _uiState.value = UiState.Loading
            getMyProfileForSettingUseCase(accessToken)
                .onSuccess { user ->
                    _settingState.value = SettingState(
                        email = user.email,
                        name = user.name,
                        notification = user.notification,
                        profileUrl = user.profileUrl
                    )
                    _uiState.value = UiState.Success
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