package com.futsalgg.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.team.model.MyTeam
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val isLoading: Boolean = false,
    val error: String? = null
)

data class MainState(
    val myTeam: MyTeam? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyTeamUseCase: GetMyTeamUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    fun getMyTeam(accessToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getMyTeamUseCase(accessToken)
                .onSuccess { myTeam ->
                    _uiState.update { it.copy(isLoading = false) }
                    _state.update { it.copy(myTeam = myTeam) }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = throwable.message ?: "알 수 없는 오류가 발생했습니다."
                        )
                    }
                }
        }
    }
}