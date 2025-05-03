package com.futsalgg.app.presentation.match.matchstat.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.GetMatchStatsUseCase
import com.futsalgg.app.domain.match.usecase.GetMatchParticipantsUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.presentation.match.matchstat.model.RoundStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class MatchStatBaseViewModel(
    private val getMatchStatsUseCase: GetMatchStatsUseCase,
    private val getMatchParticipantsUseCase: GetMatchParticipantsUseCase,
    private val matchSharedViewModel: MatchSharedViewModel,
    private val tokenManager: ITokenManager
) : ViewModel() {

    protected val uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> = uiState.asStateFlow()

    private val roundScoreState = MutableStateFlow<List<RoundStats>>(listOf())
    val roundScoreStateFlow: StateFlow<List<RoundStats>> = roundScoreState.asStateFlow()

    private val participantsState = MutableStateFlow<List<MatchParticipantState>>(listOf())
    val participantsStateFlow: StateFlow<List<MatchParticipantState>> = participantsState.asStateFlow()

    val matchState = matchSharedViewModel.matchState

    fun initial(onSuccess: (List<RoundStats>) -> Unit) {
        loadMatchStats(
            matchId = matchSharedViewModel.matchState.value.id,
            onSuccess = onSuccess
        )
        loadParticipants(
            matchId = matchSharedViewModel.matchState.value.id
        )
    }

    protected val accessToken by lazy {
        val token = tokenManager.getAccessToken()

        if (token.isNullOrEmpty()) {
            Log.e("CreateTeamViewModel", "엑세스 토큰이 존재하지 않습니다")
            uiState.value = UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다"))
        }

        token
    }

    // TODO API 업데이트후 재방문
    private fun loadMatchStats(
        matchId: String,
        onSuccess: (List<RoundStats>) -> Unit
    ) {
        viewModelScope.launch {
            try {
                uiState.value = UiState.Loading
                getMatchStatsUseCase(accessToken!!, matchId)
                    .onSuccess { domainRoundStats ->
                        val data = domainRoundStats.map { RoundStats.fromDomain(it) }
                        uiState.value = UiState.Success
                        roundScoreState.value = data
                        onSuccess(data)
                    }
                    .onFailure { error ->
                        val uiError = (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("[loadMatchStats] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        uiState.value = UiState.Error(uiError)
                    }
            } catch (e: Exception) {
                val error = UiError.UnknownError("[loadMatchStats] 예기치 않은 오류가 발생했습니다: ${e.message}")
                uiState.value = UiState.Error(error)
            }
        }
    }

    private fun loadParticipants(matchId: String) {
        viewModelScope.launch {
            try {
                getMatchParticipantsUseCase(accessToken!!, matchId)
                    .onSuccess { domainParticipants ->
                        val presentationParticipants = domainParticipants.map { MatchParticipantState.fromDomain(it) }
                        participantsState.value = presentationParticipants
                    }
                    .onFailure { error ->
                        Log.e("MatchStatBaseViewModel", "Error loading participants", error)
                    }
            } catch (e: Exception) {
                Log.e("MatchStatBaseViewModel", "Error loading participants", e)
            }
        }
    }
} 