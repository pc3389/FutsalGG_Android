package com.futsalgg.app.presentation.match.matchitem.create

import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.usecase.CreateMatchUseCase
import com.futsalgg.app.presentation.common.SharedViewModel
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.toUiError
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.match.matchitem.BaseMatchViewModel
import com.futsalgg.app.presentation.match.model.VoteStatus
import com.futsalgg.app.util.dateToRequestFormat
import com.futsalgg.app.util.toTimeRequestFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateMatchViewModel @Inject constructor(
    private val createMatchUseCase: CreateMatchUseCase,
    private val tokenManager: ITokenManager,
    private val sharedViewModel: SharedViewModel
) : BaseMatchViewModel() {

    internal fun createMatch(onSuccess: () -> Unit) {
        viewModelScope.launch {
            updateUiState(UiState.Loading)
            try {
                val accessToken = tokenManager.getAccessToken() ?: ""

                createMatchUseCase.invoke(
                    accessToken = accessToken,
                    teamId = sharedViewModel.teamId.value ?: "",
                    matchDate = matchState.value.match.matchDate.dateToRequestFormat(),
                    type = MatchType.toDomain(matchState.value.match.type),
                    location = matchState.value.match.location,
                    startTime = matchState.value.match.startTime?.takeIf { it.isNotEmpty() }?.toTimeRequestFormat(),
                    endTime = matchState.value.match.endTime?.takeIf { it.isNotEmpty() }?.toTimeRequestFormat(),
                    opponentTeamName = matchState.value.match.opponentTeamName?.takeIf { it.isNotEmpty() },
                    description = matchState.value.match.description?.takeIf { it.isNotEmpty() },
                    isVote = matchState.value.match.voteStatus != VoteStatus.NONE,
                    substituteTeamMemberId = matchState.value.match.substituteTeamMemberId?.takeIf { it.isNotEmpty() }
                ).onSuccess {
                    updateUiState(UiState.Success)
                    onSuccess()
                }.onFailure { error ->
                    updateUiState(
                        UiState.Error(
                            (error as? DomainError)?.toUiError()
                                ?: UiError.UnknownError("[createMatch] 알 수 없는 오류가 발생했습니다: ${error.message}")
                        )
                    )
                }
            } catch (e: Exception) {
                updateUiState(
                    UiState.Error(UiError.UnknownError(e.message ?: "[createMatch] 알 수 없는 오류가 발생했습니다: ${e.message}"))
                )
            }
        }
    }
} 