package com.futsalgg.app.presentation.match.matchstat.updatematchstat

import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCase
import com.futsalgg.app.domain.match.usecase.CreateMatchStatUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.matchstat.base.MatchStatBaseViewModel
import com.futsalgg.app.presentation.match.matchstat.model.MatchStat
import com.futsalgg.app.presentation.match.matchstat.model.RoundStats
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
    tokenManager: ITokenManager,
    private val createMatchStatUseCase: CreateMatchStatUseCase
) : MatchStatBaseViewModel(
    getMatchStatsUseCase,
    getMatchParticipantsUseCase,
    tokenManager
) {

    private val _createStatState = MutableStateFlow<UiState>(UiState.Initial)
    val createStatState: StateFlow<UiState> = _createStatState.asStateFlow()

    private val _tempMatchStatsState = MutableStateFlow<List<RoundStats>>(emptyList())
    val tempMatchStatsStateFlow: StateFlow<List<RoundStats>> = _tempMatchStatsState.asStateFlow()

    private val tempDeleteList: MutableList<Triple<Int, Int, List<MatchStat>>> = mutableListOf()

    init {
        super.initial(
            onSuccess = { _tempMatchStatsState.value = it }
        )
    }

    fun addGoal(roundIndex: Int, teamIndex: Int, matchParticipantId: String) {
        val currentList = _tempMatchStatsState.value.toMutableList()
        val roundList = currentList[roundIndex]
        val scoreList =
            if (teamIndex == 0) roundList.teamAStats.toMutableList() else roundList.teamBStats.toMutableList()
        scoreList.add(
            listOf(
                MatchStat(
                    matchParticipantId = matchParticipantId,
                    roundNumber = roundIndex + 1,
                    statType = MatchStat.StatType.GOAL,
                    assistedMatchStatId = null,
                )
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
        val goalList = scoreList[goalIndex].toMutableList()
        goalList.add(
            MatchStat(
                matchParticipantId = matchParticipantId,
                roundNumber = roundIndex + 1,
                statType = MatchStat.StatType.ASSIST,
                assistedMatchStatId = null,
            )
        )

        // scoreList 업데이트
        scoreList[goalIndex] = goalList

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
        tempDeleteList.add(Triple(roundIndex, teamIndex, scoreList[goalIndex]))
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

    // TODO API 수정 후 업데이트
    fun createMatchStat() {
        viewModelScope.launch {
            try {
                _createStatState.value = UiState.Loading
//                createMatchStatUseCase(
//                    matchParticipantId = matchParticipantId,
//                    roundNumber = roundNumber,
//                    statType = statType,
//                    assistedMatchStatId = assistedMatchStatId
//                )
//                    .onSuccess {
//                        _createStatState.value = UiState.Success
//                    }
//                    .onFailure { error ->
//                        uiState.value = UiState.Error(
//                            (error as? DomainError)?.toUiError()
//                                ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
//                        )
//                    }
            } catch (e: Exception) {
                uiState.value = UiState.Error(
                    UiError.UnknownError("예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }
}