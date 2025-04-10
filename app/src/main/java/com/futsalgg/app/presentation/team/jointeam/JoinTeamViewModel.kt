package com.futsalgg.app.presentation.team.jointeam

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.model.SearchTeamResponseModel
import com.futsalgg.app.domain.team.usecase.JoinTeamUseCase
import com.futsalgg.app.domain.team.usecase.SearchTeamsUseCase
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
class JoinTeamViewModel @Inject constructor(
    private val searchTeamsUseCase: SearchTeamsUseCase,
    private val joinTeamUseCase: JoinTeamUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchTeamResponseModel.Team>>(emptyList())
    val searchResults: StateFlow<List<SearchTeamResponseModel.Team>> = _searchResults.asStateFlow()

    fun searchTeams(name: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            searchTeamsUseCase(name).fold(
                onSuccess = { response ->
                    _searchResults.value = response.teams
                    _uiState.value = UiState.Success
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                }
            )
        }
    }

    fun joinTeam(teamId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            joinTeamUseCase(teamId).fold(
                onSuccess = {
                    _uiState.value = UiState.Success
                },
                onFailure = { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                }
            )
        }
    }
}