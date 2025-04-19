package com.futsalgg.app.presentation.team.jointeam

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.usecase.JoinTeamUseCase
import com.futsalgg.app.domain.team.usecase.SearchTeamsUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.team.jointeam.model.Team
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinTeamViewModel @Inject constructor(
    private val searchTeamsUseCase: SearchTeamsUseCase,
    private val joinTeamUseCase: JoinTeamUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _state = MutableStateFlow(JoinTeamState())
    val state: StateFlow<JoinTeamState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name) }
    }

    fun searchTeams(name: String) {
//        _state.update {
//            it.copy(
//                searchResults = listOf(
//                    Team(
//                        id = "1",
//                        name = "바르셀로나",
//                        createdTime = "2024-03-20"
//                    ),
//                    Team(
//                        id = "2",
//                        name = "레알마드리드",
//                        createdTime = "2024-03-19"
//                    ),
//                    Team(
//                        id = "3",
//                        name = "맨체스터유나이티드",
//                        createdTime = "2024-03-18"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    ),
//                    Team(
//                        id = "3",
//                        name = "맨체스터유나이티드",
//                        createdTime = "2024-03-18"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    ),
//                    Team(
//                        id = "3",
//                        name = "맨체스터유나이티드",
//                        createdTime = "2024-03-18"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    ),
//                    Team(
//                        id = "3",
//                        name = "맨체스터유나이티드",
//                        createdTime = "2024-03-18"
//                    ),
//                    Team(
//                        id = "4",
//                        name = "리버풀",
//                        createdTime = "2024-03-17"
//                    ),
//                    Team(
//                        id = "5",
//                        name = "아스날",
//                        createdTime = "2024-03-16"
//                    )
//                )
//            )
//        }
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            searchTeamsUseCase(name).fold(
                onSuccess = { response ->
                    _state.update {
                        it.copy(
                            searchResults = response.teams.map { team ->
                                Team(
                                    id = team.id,
                                    name = team.name,
                                    leaderName = team.leaderName,
                                    memberCount = team.memberCount,
                                    createdTime = team.createdTime
                                )
                            }
                        )
                    }
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
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            searchTeamsUseCase(name).fold(
//                onSuccess = { response ->
//                    _state.update {
//                        it.copy(
//                            searchResults = response.teams.map { team ->
//                                Team(
//                                    id = team.id,
//                                    name = team.name,
//                                    leaderName = team.leaderName,
//                                    memberCount = team.memberCount,
//                                    createdTime = team.createdTime
//                                )
//                            }
//                        )
//                    }
//                    _uiState.value = UiState.Success
//                },
//                onFailure = { error ->
//                    _uiState.value = UiState.Error(
//                        (error as? DomainError)?.toUiError()
//                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
//                    )
//                }
//            )
//        }
    }

    fun joinTeam(
        teamId: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val accessToken = tokenManager.getAccessToken()

            if (accessToken.isNullOrEmpty()) {
                Log.e("CreateUserViewModel", "엑세스 토큰이 존재하지 않습니다")
                _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                return@launch
            }

            joinTeamUseCase(
                accessToken = accessToken,
                teamId = teamId
            ).fold(
                onSuccess = {
                    _uiState.value = UiState.Success
                    onSuccess()
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

    fun onTeamSelected(team: Team) {
        if (_state.value.selectedTeamId == team.id) {
            _state.value = JoinTeamState()
        } else {
            _state.value = _state.value.copy(
                selectedTeamId = team.id,
                name = team.name,
                buttonEnabled = true
            )
        }
    }
}