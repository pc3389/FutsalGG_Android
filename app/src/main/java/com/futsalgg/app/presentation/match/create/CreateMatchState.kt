package com.futsalgg.app.presentation.match.create

import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.model.MatchType

data class CreateMatchState(
    val teamId: String = "",
    val matchDate: String = "",
    val matchDateState: DateState = DateState.Initial,
    val type: MatchType = MatchType.INTRA_SQUAD,
    val location: String = "",
    val startTime: String = "",
    val knowsStartTime: Boolean = false,
    val endTime: String = "",
    val knowsEndTime: Boolean = false,
    val opponentTeamName: String = "",
    val substituteTeamMemberId: String = "",
    val description: String = "",
    val isVote: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = matchDateState == DateState.Available &&
                location.isNotEmpty()

} 