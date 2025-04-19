package com.futsalgg.app.presentation.match

import androidx.lifecycle.ViewModel
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.main.model.TeamRole
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.presentation.match.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatchSharedViewModel @Inject constructor() : ViewModel() {
    private val _selectedMatchId = MutableStateFlow("")
    val selectedMatchId: StateFlow<String> = _selectedMatchId

    private val _matchParticipantsState = MutableStateFlow<List<MatchParticipantState>>(emptyList())
    val matchParticipantsState: StateFlow<List<MatchParticipantState>> =
        _matchParticipantsState.asStateFlow()

    private val _matchState = MutableStateFlow(Match())
    val matchState: StateFlow<Match> = _matchState.asStateFlow()

    fun updateMatchParticipantsState(newList: List<MatchParticipantState>) {
        _matchParticipantsState.value = newList
    }

    fun updateSelectedMatchId(id: String) {
        _selectedMatchId.value = id
    }

    fun updateParticipantSubteam(index: Int) {
        val currentList = _matchParticipantsState.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] =
                currentList[index].copy(
                    subTeam = if (currentList[index].subTeam == MatchParticipantState.SubTeam.A) {
                        MatchParticipantState.SubTeam.B
                    } else MatchParticipantState.SubTeam.A
                )
            _matchParticipantsState.value = currentList
        }
    }

    fun updateMatch(match: Match) {
        _matchState.value = match
    }

    init {
        // TODO Remove Stub..!
        _matchState.value = Match(
            matchDate = "2025-04-18",
            startTime = "12:34",
            endTime = "06:12",
            location = "장소장소장소",
            type = MatchType.INTRA_SQUAD
        )
        _matchParticipantsState.value = listOf(
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_SECRETARY,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_DEPUTY_LEADER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            ),
            MatchParticipantState(
                id = "Match Participant ID",
                matchId = "matchId",
                teamMemberId = "teamMemberId1",
                name = "닉네임닉네임닉네임쓰",
                role = TeamRole.TEAM_MEMBER,
                profileUrl = "",
                subTeam = MatchParticipantState.SubTeam.NONE,
                createdTime = "2015.12.03"
            )
        )
    }
}