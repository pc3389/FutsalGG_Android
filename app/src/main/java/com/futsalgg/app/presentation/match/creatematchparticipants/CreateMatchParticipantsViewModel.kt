package com.futsalgg.app.presentation.match.creatematchparticipants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.CreateMatchParticipantsUseCase
import com.futsalgg.app.domain.team.model.TeamMemberStatus
import com.futsalgg.app.domain.team.usecase.GetTeamMembersByTeamIdUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMatchParticipantsViewModel @Inject constructor(
    private val createMatchParticipantsUseCase: CreateMatchParticipantsUseCase,
    private val matchSharedViewModel: MatchSharedViewModel,
    private val getTeamMembersByTeamIdUseCase: GetTeamMembersByTeamIdUseCase,
    private val sharedViewModel: SharedViewModel,
    tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchParticipantsState = MutableStateFlow<List<MatchParticipantState>>(emptyList())
    val matchParticipantsState: StateFlow<List<MatchParticipantState>> =
        _matchParticipantsState.asStateFlow()

    private val _createMatchParticipantsState = MutableStateFlow(CreateMatchParticipantsState())

    val matchState = matchSharedViewModel.matchState

    private val _isAllSelected = MutableStateFlow(false)

    private val accessToken = tokenManager.getAccessToken() ?: ""

    init {
        loadTeamMembers()
    }

    private fun loadTeamMembers() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {

                getTeamMembersByTeamIdUseCase(accessToken, sharedViewModel.teamId.value ?: "")
                    .onSuccess { teamMembers ->
                        val activeMembers = teamMembers.filter { it.status == TeamMemberStatus.ACTIVE }
                        _matchParticipantsState.value = activeMembers.map { member ->
                            MatchParticipantState(
                                id = "",
                                matchId = matchSharedViewModel.matchState.value.id,
                                teamMemberId = member.id,
                                name = member.name,
                                role = TeamRole.fromDomain(member.role),
                                profileUrl = member.profileUrl ?: "",
                                subTeam = MatchParticipantState.SubTeam.NONE,
                                isSelected = false,
                                createdTime = member.createdTime
                            )
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

    fun addTeamMember(teamMemberId: String) {
        val currentList = _createMatchParticipantsState.value.teamMemberIds.toMutableList()
        if (!currentList.contains(teamMemberId)) {
            currentList.add(teamMemberId)
            _createMatchParticipantsState.value = _createMatchParticipantsState.value.copy(
                teamMemberIds = currentList
            )
        }
    }

    fun removeTeamMember(teamMemberId: String) {
        val currentList = _createMatchParticipantsState.value.teamMemberIds.toMutableList()
        currentList.remove(teamMemberId)
        _createMatchParticipantsState.value = _createMatchParticipantsState.value.copy(
            teamMemberIds = currentList
        )
    }

    fun toggleAllSelection() {
        val currentList = _matchParticipantsState.value.toMutableList()
        val shouldSelectAll = !_isAllSelected.value

        currentList.forEachIndexed { index, _ ->
            currentList[index] = currentList[index].copy(isSelected = shouldSelectAll)
        }

        _matchParticipantsState.value = currentList
        _isAllSelected.value = shouldSelectAll
    }

    private fun updateAllSelectedIndicator() {
        _isAllSelected.value = _matchParticipantsState.value.isNotEmpty() &&
                _matchParticipantsState.value.all { it.isSelected }
    }

    fun updateIsSelected(index: Int) {
        val currentList = _matchParticipantsState.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] =
                currentList[index].copy(isSelected = !currentList[index].isSelected)
            _matchParticipantsState.value = currentList
            updateAllSelectedIndicator()
        }
    }

    fun createMatchParticipants(onSuccess: () -> Unit) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                createMatchParticipantsUseCase(
                    accessToken = accessToken,
                    matchId = matchState.value.id,
                    teamMemberIds = _createMatchParticipantsState.value.teamMemberIds
                )
                    .onSuccess { participants ->
                        _matchParticipantsState.value = participants.map { participant ->
                            MatchParticipantState(
                                id = participant.id,
                                matchId = participant.matchId,
                                teamMemberId = participant.teamMemberId,
                                name = participant.name,
                                role = TeamRole.fromDomain(participant.role),
                                profileUrl = participant.profileUrl,
                                subTeam = when (participant.subTeam.name) {
                                    "A" -> MatchParticipantState.SubTeam.A
                                    else -> MatchParticipantState.SubTeam.B
                                },
                                createdTime = participant.createdTime
                            )
                        }
                        updateMatchParticipantsState(_matchParticipantsState.value)
                        onSuccess()
                        _uiState.value = UiState.Success
                    }
                    .onFailure { error ->
                        _uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[createMatchParticipants] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[createMatchParticipants] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    private fun updateMatchParticipantsState(newList: List<MatchParticipantState>) {
        matchSharedViewModel.updateMatchParticipantsState(newList)
    }
}