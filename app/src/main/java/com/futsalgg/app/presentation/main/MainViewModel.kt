package com.futsalgg.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.main.model.MyTeam
import com.futsalgg.app.presentation.main.model.MyTeamMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainState(
    val myTeam: MyTeam? = null
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getMyTeamUseCase: GetMyTeamUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _mainState = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> = _mainState.asStateFlow()

    fun getMyTeam(accessToken: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            getMyTeamUseCase(accessToken)
                .onSuccess { domainMyTeam ->
                    _uiState.value = UiState.Success
                    _mainState.update { it.copy(myTeam = MyTeamMapper.toPresentation(domainMyTeam)) }
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