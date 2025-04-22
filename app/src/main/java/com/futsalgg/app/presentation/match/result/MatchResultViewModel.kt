package com.futsalgg.app.presentation.match.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchResultViewModel @Inject constructor(
    private val getMatchesUseCase: GetMatchesUseCase,
    sharedViewModel: SharedViewModel,
    private val matchSharedViewModel: MatchSharedViewModel,
    tokenManager: ITokenManager
) : ViewModel() {

    val accessToken = tokenManager.getAccessToken()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchesByDate = MutableStateFlow<Map<String, List<Match>>>(emptyMap())
    val matchesByDate: StateFlow<Map<String, List<Match>>> = _matchesByDate.asStateFlow()

    val teamId = sharedViewModel.teamId.value

    init {
        loadMatches()
    }

    fun updateMatch(match: Match) {
        matchSharedViewModel.updateMatch(match)
    }

    private fun loadMatches() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                    _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                    return@launch
                }

                getMatchesUseCase.invoke(
                    accessToken = accessToken,
                    teamId = teamId ?: ""
                ).onSuccess { matches ->
                    _matchesByDate.value = matches
                        .map { it.toPresentation() }
                        .groupBy { it.matchDate }
                    _uiState.value = UiState.Success
                }.onFailure { throwable ->
                    val error = UiState.Error(
                        (throwable as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("알 수 없는 오류가 발생했습니다.")
                    )
                    _uiState.value = error
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(
                    UiError.UnknownError("예기치 않은 오류가 발생했습니다: ${e.message}")
                )
            }
        }
    }

    private fun deleteMatch(match: Match) {

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