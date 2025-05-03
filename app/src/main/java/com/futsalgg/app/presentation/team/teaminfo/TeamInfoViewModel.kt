package com.futsalgg.app.presentation.team.teaminfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.team.model.TeamMemberStatus
import com.futsalgg.app.domain.team.usecase.GetMyTeamUseCase
import com.futsalgg.app.domain.team.usecase.GetTeamMembersByTeamIdUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
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

@HiltViewModel
class TeamInfoViewModel @Inject constructor(
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase,
    private val getMyTeamUseCase: GetMyTeamUseCase,
    private val sharedViewModel: SharedViewModel,
    private val tokenManager: ITokenManager
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _teamMembersState = MutableStateFlow<List<TeamMemberState>>(emptyList())
    val teamMembersState: StateFlow<List<TeamMemberState>> =
        _teamMembersState.asStateFlow()

    private val _teamState = MutableStateFlow(MyTeam())
    val teamState: StateFlow<MyTeam> = _teamState.asStateFlow()

    val accessToken = tokenManager.getAccessToken() ?: ""

    init {
        loadTeamMembers()
        getMyTeam()
//        _teamMembersState.value = listOf(
//            TeamMemberState(),
//            TeamMemberState(),
//            TeamMemberState(),
//            TeamMemberState(),
//            TeamMemberState(),
//            TeamMemberState()
//        )
    }

    private fun loadTeamMembers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {

                getTeamMembersByTeamIdUseCase(accessToken, sharedViewModel.teamId.value ?: "")
                    .onSuccess { teamMembers ->
                        val activeMembers =
                            teamMembers.filter { it.status == TeamMemberStatus.ACTIVE }
                        _teamMembersState.value = activeMembers.map { member ->
                            TeamMemberState.fromDomain(member)
                        }
                        _uiState.value = UiState.Success
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[loadTeamMembers] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[loadTeamMembers] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    fun getMyTeam() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            try {
                getMyTeamUseCase(accessToken)
                    .onSuccess { domainMyTeam ->
                        _uiState.value = UiState.Success
                        _teamState.update { MyTeamMapper.toPresentation(domainMyTeam) }
                        sharedViewModel.setTeamId(domainMyTeam.id)
                        sharedViewModel.setMyTeamMemberId(domainMyTeam.teamMemberId)
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[getMyTeam] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }

            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[getMyTeam] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    fun setSelectedTeamMemberId(id: String) {
        sharedViewModel.setSelectedTeamMemberId(id)
    }
}