package com.futsalgg.app.presentation.match.result

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.match.model.Match as DomainMatch
import com.futsalgg.app.domain.match.usecase.GetMatchesUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.presentation.match.model.MatchStatus
import com.futsalgg.app.presentation.common.model.MatchType
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
    tokenManager: ITokenManager
) : ViewModel() {

    val accessToken = tokenManager.getAccessToken()

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _matchesByDate = MutableStateFlow<Map<String, List<Match>>>(emptyMap())
    val matchesByDate: StateFlow<Map<String, List<Match>>> = _matchesByDate.asStateFlow()

    init {
        loadStubData()
    }

    private fun loadStubData() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val stubMatches = listOf(
                Match(
                    id = "1123123123",
                    opponentTeamName = "상대팀",
                    description = "팀",
                    type = MatchType.INTER_TEAM,
                    matchDate = "2025-04-19",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.DRAFT,
                    createdTime = "TODO()"
                ),
                Match(
                    id = "1123123123",
                    opponentTeamName = null,
                    description = "Description",
                    type = MatchType.INTRA_SQUAD,
                    matchDate = "2025-04-19",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.ONGOING,
                    createdTime = "TODO()"
                ),
                Match(
                    id = "1123123123",
                    opponentTeamName = "상대팀",
                    description = "팀",
                    type = MatchType.INTER_TEAM,
                    matchDate = "2025-04-15",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.COMPLETED,
                    createdTime = "TODO()"
                ),
                Match(
                    id = "1123123123",
                    opponentTeamName = null,
                    description = "Description",
                    type = MatchType.INTRA_SQUAD,
                    matchDate = "2012-12-31",
                    startTime = "05:12",
                    endTime = "05:12",
                    location = "Location",
                    voteStatus = VoteStatus.NONE,
                    status = MatchStatus.COMPLETED,
                    createdTime = "TODO()"
                )
            )
            _matchesByDate.value = stubMatches.groupBy { it.matchDate }
            _uiState.value = UiState.Success
        }
    }

    private fun loadMatches() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            if (accessToken.isNullOrEmpty()) {
                Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
                _uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
                return@launch
            }

            getMatchesUseCase.invoke(
                accessToken = accessToken,
            ).onSuccess { matches ->
                _matchesByDate.value = matches
                    .map { it.toPresentation() }
                    .groupBy { it.matchDate }
                _uiState.value = UiState.Success
            }.onFailure { error ->
                _uiState.value = UiState.Error(UiError.UnknownError(error.message ?: "알 수 없는 오류가 발생했습니다"))
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