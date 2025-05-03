package com.futsalgg.app.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.usecase.RefreshTokenUseCase
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import com.futsalgg.app.domain.common.model.Gender
import com.futsalgg.app.domain.user.usecase.GetMyProfileForSettingUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenManager: ITokenManager,
    private val getMyProfileForSettingUseCase: GetMyProfileForSettingUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val getMyTeamUseCase: GetMyTeamUseCase
) : ViewModel() {

    private val _splashState = MutableStateFlow(SplashState())
    val splashState: StateFlow<SplashState> = _splashState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        getProfile()
    }

    fun splashStateToFalse() {
        _splashState.value = SplashState()
    }

    private fun getProfile() {
        viewModelScope.launch {
            delay(1000)
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    _splashState.value = _splashState.value.copy(
                        toLogin = true
                    )
                    return@launch
                }

                getMyProfileForSettingUseCase(accessToken)
                    .onSuccess { user ->
                        if (user.gender == Gender.NONE) {
                            _splashState.value = _splashState.value.copy(
                                toCreateUser = true
                            )
                        } else {
                            getMyTeam(accessToken)
                        }
                    }
                    .onFailure { error ->
                        if (error.message.equals("UNAUTHORIZED_TOKEN_AUTHENTICATION_FAILED")) {
                            refreshTokenUseCase()
                        }
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[getProfile] 알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("[getProfile] 알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    private fun refreshTokenUseCase() {
        viewModelScope.launch {
            val refreshToken = tokenManager.getRefreshToken()

            if (refreshToken.isNullOrEmpty()) {
                _splashState.value = _splashState.value.copy(
                    toLogin = true
                )
                return@launch
            }

            try {
                refreshTokenUseCase(refreshToken)
                    .onSuccess {
                        tokenManager.saveTokens(
                            it.accessToken, it.refreshToken
                        )
                        getProfile()
                    }
                    .onFailure { error ->
                        _splashState.value = _splashState.value.copy(
                            toLogin = true
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("[refreshTokenUseCase] 알 수 없는 오류가 발생했습니다."))
            }
        }
    }

    private fun getMyTeam(accessToken: String) {
        viewModelScope.launch {
            try {
                getMyTeamUseCase(accessToken)
                    .onSuccess {
                        _splashState.value = _splashState.value.copy(
                            toMain = true
                        )
                    }
                    .onFailure { error ->
                        if (error.message == "NOT_FOUND_TEAM_ID") {
                            _splashState.value = _splashState.value.copy(
                                toSelectTeam = true
                            )
                        }
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[getMyTeam] 알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(UiError.UnknownError("[getMyTeam] 알 수 없는 오류가 발생했습니다."))
            }
        }
    }

}