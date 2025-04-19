package com.futsalgg.app.presentation.match.creatematchparticipants

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.CreateMatchParticipantsUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchUseCase
import com.futsalgg.app.domain.team.usecase.GetTeamMembersUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.main.model.TeamRole
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.util.toFullDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CreateMatchParticipantsViewModel @Inject constructor(
    private val createMatchParticipantsUseCase: CreateMatchParticipantsUseCase,
    private val getTeamMembersUseCase: GetTeamMembersUseCase,
    private val getMatchUseCase: GetMatchUseCase,
    private val tokenManager: ITokenManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchParticipantsState = MutableStateFlow<List<MatchParticipantState>>(emptyList())
    val matchParticipantsState: StateFlow<List<MatchParticipantState>> =
        _matchParticipantsState.asStateFlow()

    private val _createMatchParticipantsState = MutableStateFlow(CreateMatchParticipantsState())

    private val _matchState = MutableStateFlow(Match())
    val matchState: StateFlow<Match> = _matchState.asStateFlow()

    private val _isAllSelected = MutableStateFlow(false)

    // TODO Match Participants api

    init {
        // TODO Remove Stub..!
        _matchState.value = Match(
            matchDate = ("2025-04-18").toFullDateFormat(),
            startTime = "12:34",
            endTime = "06:12",
            location = "장소장소장소",
        )
        _matchParticipantsState.value = listOf(
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER.displayName,
                profileUrl = "",
                subTeam = SubTeam.NONE,
                createdTime = "2015.12.03"
            )
        )
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

    fun createMatchParticipants() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                createMatchParticipantsUseCase(
                    accessToken = accessToken,
                    matchId = _createMatchParticipantsState.value.matchId,
                    teamMemberIds = _createMatchParticipantsState.value.teamMemberIds
                )
                    .onSuccess { participants ->
                        _matchParticipantsState.value = participants.map { participant ->
                            MatchParticipantState(
                                id = participant.id,
                                matchId = participant.matchId,
                                teamMemberId = participant.teamMemberId,
                                name = participant.name,
                                role = participant.role,
                                profileUrl = "",
                                subTeam = when (participant.subTeam.name) {
                                    "A" -> SubTeam.A
                                    else -> SubTeam.B
                                },
                                createdTime = participant.createdTime
                            )
                        }
                        _uiState.value = UiState.Success
                    }
                    .onFailure { throwable ->
                        _uiState.value = UiState.Error(
                            (throwable as? DomainError)?.toUiError()
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

    fun loadMatch(matchId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateMatchParticipantsViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                getMatchUseCase(
                    accessToken = accessToken,
                    id = matchId
                )
                    .onSuccess { match ->
                        _matchState.value = _matchState.value.copy(
                            id = match.id,
                            opponentTeamName = match.opponentTeamName,
                            type = MatchType.fromDomain(match.type),
                            matchDate = match.matchDate.toFullDateFormat(),
                            startTime = match.startTime ?: "00:00",
                            endTime = match.endTime ?: "00:00",
                            location = match.location,
                        )
                        _uiState.value = UiState.Success
                    }
                    .onFailure { throwable ->
                        _uiState.value = UiState.Error(
                            (throwable as? DomainError)?.toUiError()
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