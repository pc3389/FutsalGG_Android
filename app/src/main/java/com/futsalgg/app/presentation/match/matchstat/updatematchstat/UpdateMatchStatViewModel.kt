package com.futsalgg.app.presentation.match.matchstat.updatematchstat

import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.model.CreateBulkMatchStat
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCase
import com.futsalgg.app.domain.match.usecase.CreateMatchStatsBulkUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchstat.base.MatchStatBaseViewModel
import com.futsalgg.app.presentation.match.matchstat.model.MatchStat
import com.futsalgg.app.presentation.match.matchstat.model.MatchStat.StatType
import com.futsalgg.app.presentation.match.matchstat.model.RoundStats
import com.futsalgg.app.presentation.match.matchstat.model.TeamStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMatchStatViewModel @Inject constructor(
    getMatchStatsUseCase: GetMatchStatsUseCase,
    getMatchParticipantsUseCase: GetMatchParticipantsUseCase,
    private val createMatchStatsBulkUseCase: CreateMatchStatsBulkUseCase,
    tokenManager: ITokenManager,
    matchSharedViewModel: MatchSharedViewModel
) : MatchStatBaseViewModel(
    getMatchStatsUseCase,
    getMatchParticipantsUseCase,
    matchSharedViewModel,
    tokenManager
) {

    private val _createStatState = MutableStateFlow<UiState>(UiState.Initial)

    private val _tempMatchStatsState = MutableStateFlow<List<RoundStats>>(emptyList())
    val tempMatchStatsStateFlow: StateFlow<List<RoundStats>> = _tempMatchStatsState.asStateFlow()

    private val tempDeleteList: MutableList<Triple<Int, Int, List<MatchStat>>> = mutableListOf()

    private val matchRounds = matchSharedViewModel.matchRound

    init {
        super.initial(
            onSuccess = { roundStat ->
                _tempMatchStatsState.value = roundStat
                if (_tempMatchStatsState.value.size != matchRounds.value) {
                    for (i in 1..matchRounds.value) {
                        if (_tempMatchStatsState.value.find { it.roundNumber == i } == null)
                            _tempMatchStatsState.value += RoundStats(
                                roundNumber = i,
                                teamAStats = listOf(),
                                teamBStats = listOf()
                            )
                    }
                }
            }
        )
    }

    fun addGoal(roundIndex: Int, teamIndex: Int, matchParticipantId: String) {
        val currentList = _tempMatchStatsState.value.toMutableList()
        val roundList = currentList[roundIndex]
        val scoreList =
            if (teamIndex == 0) roundList.teamAStats.toMutableList() else roundList.teamBStats.toMutableList()
        
        scoreList.add(
            TeamStats(
                goal = MatchStat(
                    matchParticipantId = matchParticipantId,
                    roundNumber = roundIndex + 1,
                    statType = StatType.GOAL,
                    assistedMatchStatId = null,
                ),
                assist = null
            )
        )

        // roundList 업데이트
        val updatedRoundList = if (teamIndex == 0) {
            roundList.copy(teamAStats = scoreList)
        } else {
            roundList.copy(teamBStats = scoreList)
        }

        // 전체 리스트 업데이트
        currentList[roundIndex] = updatedRoundList
        _tempMatchStatsState.value = currentList
    }

    fun addAssist(roundIndex: Int, teamIndex: Int, goalIndex: Int, matchParticipantId: String) {
        val currentList = _tempMatchStatsState.value.toMutableList()
        val roundList = currentList[roundIndex]
        val scoreList =
            if (teamIndex == 0) roundList.teamAStats.toMutableList() else roundList.teamBStats.toMutableList()
        val goalStat = scoreList[goalIndex]
        
        scoreList[goalIndex] = goalStat.copy(
            assist = MatchStat(
                matchParticipantId = matchParticipantId,
                roundNumber = roundIndex + 1,
                statType = StatType.ASSIST,
                assistedMatchStatId = null,
            )
        )

        // roundList 업데이트
        val updatedRoundList = if (teamIndex == 0) {
            roundList.copy(teamAStats = scoreList)
        } else {
            roundList.copy(teamBStats = scoreList)
        }

        // 전체 리스트 업데이트
        currentList[roundIndex] = updatedRoundList
        _tempMatchStatsState.value = currentList
    }

    fun deleteGoal(roundIndex: Int, teamIndex: Int, goalIndex: Int) {
        val currentList = _tempMatchStatsState.value.toMutableList()
        val roundList = currentList[roundIndex]
        val scoreList =
            if (teamIndex == 0) roundList.teamAStats.toMutableList() else roundList.teamBStats.toMutableList()
        tempDeleteList.add(Triple(roundIndex, teamIndex, scoreList[goalIndex].goal?.let { listOf(it) } ?: emptyList()))
        scoreList.removeAt(goalIndex)

        // roundList 업데이트
        val updatedRoundList = if (teamIndex == 0) {
            roundList.copy(teamAStats = scoreList)
        } else {
            roundList.copy(teamBStats = scoreList)
        }

        // 전체 리스트 업데이트
        currentList[roundIndex] = updatedRoundList
        _tempMatchStatsState.value = currentList
    }

    private fun getMatchStats(): List<CreateBulkMatchStat> {
        val list: MutableList<CreateBulkMatchStat> = mutableListOf()
        _tempMatchStatsState.value.forEach { round ->
            round.teamAStats.forEach { stats ->
                list.add(
                    CreateBulkMatchStat(
                        roundNumber = round.roundNumber,
                        subTeam = "A",
                        goalMatchParticipantId = stats.goal?.matchParticipantId ?: "",
                        assistMatchParticipantId = stats.assist?.matchParticipantId
                    )
                )
            }
            round.teamBStats.forEach { stats ->
                list.add(
                    CreateBulkMatchStat(
                        roundNumber = round.roundNumber,
                        subTeam = "B",
                        goalMatchParticipantId = stats.goal?.matchParticipantId ?: "",
                        assistMatchParticipantId = stats.assist?.matchParticipantId
                    )
                )
            }
        }
        return list
    }

    fun createMatchStat() {
        viewModelScope.launch {
            try {
                _createStatState.value = UiState.Loading
                createMatchStatsBulkUseCase(
                    accessToken = accessToken!!,
                    matchId = matchState.value.id,
                    stats = getMatchStats(),
                )
                    .onSuccess {
                        _createStatState.value = UiState.Success
                    }
                    .onFailure { error ->
                        uiState.value = UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[createMatchStat 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    }
            } catch (e: Exception) {
                uiState.value = UiState.Error(
                    UiError.UnknownError("[createMatchStat] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }
}