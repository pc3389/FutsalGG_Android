package com.futsalgg.app.presentation.match.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.DeleteMatchUseCase
import com.futsalgg.app.domain.match.model.Match as DomainMatch
import com.futsalgg.app.domain.match.usecase.GetMatchesUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.presentation.match.model.MatchStatus
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.model.VoteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchResultViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    private val deleteMatchUseCase: DeleteMatchUseCase,
    sharedViewModel: SharedViewModel,
    private val matchSharedViewModel: MatchSharedViewModel,
    tokenManager: ITokenManager
) : ViewModel() {

    val accessToken = tokenManager.getAccessToken() ?: ""

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchesByDate = MutableStateFlow<Map<String, List<Match>>>(emptyMap())
    val matchesByDate: StateFlow<Map<String, List<Match>>> = _matchesByDate.asStateFlow()

    val teamId = sharedViewModel.teamId.value

    val shouldRefresh = matchSharedViewModel.shouldRefresh

    init {
        loadMatches()
    }

    fun updateMatch(match: Match) {
        matchSharedViewModel.updateMatch(match)
    }

    private fun removeMatch(match: Match) {
        _matchesByDate.update { currentMatches ->
            val date = match.matchDate
            val updatedMatches = currentMatches[date]?.filter { it.id != match.id }

            if (updatedMatches.isNullOrEmpty()) {
                // 빈 리스트가 된 경우 해당 날짜 제거
                currentMatches - date
            } else {
                // 해당 날짜의 리스트만 업데이트
                currentMatches + (date to updatedMatches)
            }
        }
    }

    fun loadMatches() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {

                getMatchesUseCase.invoke(
                    accessToken = accessToken,
                    teamId = teamId ?: ""
                ).onSuccess { matches ->
                    _matchesByDate.value = matches
                        .map { it.toPresentation() }
                        .groupBy { it.matchDate }
                    matchSharedViewModel.afterRefresh()
                    _uiState.value = UiState.Success
                }.onFailure { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("[loadMatches] 알 수 없는 오류가 발생했습니다: ${error.message}")
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[loadMatches] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    fun deleteMatch(match: Match) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                deleteMatchUseCase.invoke(
                    accessToken = accessToken,
                    id = match.id
                ).onSuccess { _ ->
                    removeMatch(match)
                    _uiState.value = UiState.Success
                }.onFailure { error ->
                    _uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("[deleteMatch] 알 수 없는 오류가 발생했습니다: ${error.message}")
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("[deleteMatch] 예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    private fun DomainMatch.toPresentation(): Match {
        return Match(
            id = id,
            type = MatchType.fromDomain(type),
            opponentTeamName = opponentTeamName,
            location = location,
            matchDate = matchDate,
            startTime = startTime,
            endTime = endTime,
            description = description,
            voteStatus = VoteStatus.fromDomain(voteStatus),
            status = MatchStatus.fromDomain(status),
            createdTime = createdTime
        )
    }
}