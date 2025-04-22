package com.futsalgg.app.presentation.match.matchitem.update

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.presentation.common.error.UiError
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
                val accessToken = tokenManager.getAccessToken()

                if (accessToken.isNullOrEmpty()) {
                    Log.e("CreateMatchViewModel", "엑세스 토큰이 존재하지 않습니다")
                    updateUiState(UiState.Error(UiError.AuthError("엑세스 토큰이 존재하지 않습니다")))
                    return@launch
                }
//
//                val result = createMatchUseCase(
//                    accessToken = accessToken,
//                    teamId = matchState.value.teamId,
//                    matchDate = matchState.value.matchDate.dateToRequestFormat(),
//                    type = MatchType.toDomain(matchState.value.type),
//                    location = matchState.value.location,
//                    startTime = matchState.value.startTime.takeIf { it.isNotEmpty() },
//                    endTime = matchState.value.endTime.takeIf { it.isNotEmpty() },
//                    opponentTeamName = matchState.value.opponentTeamName.takeIf { it.isNotEmpty() },
//                    description = matchState.value.description.takeIf { it.isNotEmpty() },
//                    isVote = matchState.value.isVote,
//                    substituteTeamMemberId = matchState.value.substituteTeamMemberId.takeIf { it.isNotEmpty() }
//                )
//
//                if (result.isSuccess) {
//                    updateUiState(UiState.Success)
//                    onSuccess()
//                } else {
//                    Log.e("CreateMatchViewModel", "알 수 없는 오류가 발생했습니다")
//                    updateUiState(
//                        UiState.Error(
//                            UiError.UnknownError(
//                                result.exceptionOrNull()?.message ?: "알 수 없는 오류가 발생했습니다"
//                            )
//                        )
//                    )
//                }
            } catch (e: Exception) {
                Log.e("CreateMatchViewModel", "알 수 없는 오류가 발생했습니다", e)
                updateUiState(
                    UiState.Error(UiError.UnknownError(e.message ?: "알 수 없는 오류가 발생했습니다"))
                )
            }
        }
    }
}