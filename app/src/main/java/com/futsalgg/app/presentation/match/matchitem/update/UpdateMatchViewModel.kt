package com.futsalgg.app.presentation.match.matchitem.update

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.UpdateMatchUseCase
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchitem.BaseMatchViewModel
import com.futsalgg.app.presentation.match.matchitem.MatchState
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.util.dateToRequestFormat
import com.futsalgg.app.util.toDateFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateMatchViewModel @Inject constructor(
    private val tokenManager: ITokenManager,
    private val sharedViewModel: MatchSharedViewModel,
    private val updateMatchUseCase: UpdateMatchUseCase,
    private val matchSharedViewModel: MatchSharedViewModel
): BaseMatchViewModel() {

    init {
        matchState.value = MatchState(
            match = Match(
                id = sharedViewModel.matchState.value.id,
                opponentTeamName = sharedViewModel.matchState.value.opponentTeamName,
                description = sharedViewModel.matchState.value.description,
                type = sharedViewModel.matchState.value.type,
                matchDate = sharedViewModel.matchState.value.matchDate.toDateFormat(
                    "yyyyMMdd"
                ),
                startTime = sharedViewModel.matchState.value.startTime,
                endTime = sharedViewModel.matchState.value.endTime,
                location = sharedViewModel.matchState.value.location,
                voteStatus = sharedViewModel.matchState.value.voteStatus,
                status = sharedViewModel.matchState.value.status,
                substituteTeamMemberId = sharedViewModel.matchState.value.substituteTeamMemberId,
                createdTime = sharedViewModel.matchState.value.createdTime
            ),
            matchDateState = DateState.Available,
            knowsStartTime = !sharedViewModel.matchState.value.startTime.isNullOrEmpty(),
            knowsEndTime = !sharedViewModel.matchState.value.endTime.isNullOrEmpty(),
        )
    }

    internal fun updateMatch(onSuccess: () -> Unit) {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            try {
                val accessToken = tokenManager.getAccessToken() ?: ""

                updateMatchUseCase.invoke(
                    accessToken = accessToken,
                    matchId = sharedViewModel.matchState.value.id,
                    matchDate = matchState.value.match.matchDate.dateToRequestFormat(),
                    location = matchState.value.match.location,
                    startTime = matchState.value.match.startTime.takeIf { !it.isNullOrEmpty()},
                    endTime = matchState.value.match.endTime.takeIf { !it.isNullOrEmpty() },
                    substituteTeamMemberId = matchState.value.match.substituteTeamMemberId.takeIf { !it.isNullOrEmpty() }
                ).onSuccess {
                    updateUiState(UiState.Success)
                    matchSharedViewModel.shouldRefresh()
                    onSuccess()
                }.onFailure { error ->
                    uiState.value = UiState.Error(
                        (error as? DomainError)?.toUiError()
                            ?: UiError.UnknownError("[updateMatch] 알 수 없는 오류가 발생했습니다: ${error.message}")
                    )
                }
            } catch (e: Exception) {
                updateUiState(
                    UiState.Error(UiError.UnknownError(e.message ?: "[updateMatch] 알 수 없는 오류가 발생했습니다: ${e.message}"))
                )
            }
        }
    }
}