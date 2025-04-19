package com.futsalgg.app.presentation.match.updateround

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.UpdateMatchRoundsUseCase
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
class UpdateMatchRoundViewModel @Inject constructor(
    private val updateMatchRoundsUseCase: UpdateMatchRoundsUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun updateRounds(matchId: String, rounds: Int, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateMatchParticipantsViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                _uiState.value = UiState.Loading
                updateMatchRoundsUseCase(accessToken, matchId, rounds)
                    .onSuccess {
                        _uiState.value = UiState.Success
                        onSuccess()
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }
}