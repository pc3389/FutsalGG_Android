package com.futsalgg.app.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.usecase.AuthUseCase
import com.futsalgg.app.domain.common.error.DomainError
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
class LoginViewModel @Inject constructor(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun setLoading() {
        _uiState.value = UiState.Loading
    }

    fun setError() {
        _uiState.value = UiState.Error (
            UiError.UnknownError("[Login] 예상치 못한 에러")
        )
    }

    fun signInWithGoogleIdToken(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = authUseCase(idToken)
            result.onSuccess {
                _uiState.value = UiState.Success
                onSuccess()
            }.onFailure { error ->
                _uiState.value = UiState.Error(
                    (error as? DomainError)?.toUiError()
                        ?: UiError.UnknownError("[signInWithGoogleIdToken] 알 수 없는 오류가 발생했습니다: ${error.message}")
                )
                onFailure(error)
            }
        }
    }
}