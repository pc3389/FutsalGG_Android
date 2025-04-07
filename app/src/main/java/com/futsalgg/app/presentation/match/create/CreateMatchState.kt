package com.futsalgg.app.presentation.match.create

import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.match.model.MatchType

data class CreateMatchState(
    val matchDate: String = "",
    val matchDateState: DateState = DateState.Initial,
    val type: MatchType = MatchType.INTER_TEAM,
    val opponentTeamName: String = "",
    val opponentTeamNameState: EditTextState = EditTextState.Initial,
    val location: String = "",
    val startTime: String = "",
    val startTimeState: EditTextState = EditTextState.Initial,
    val endTime: String = "",
    val endTimeState: EditTextState = EditTextState.Initial,
    val description: String = "",
    val teamId: String = "",
    val isVote: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = matchDateState == DateState.Available &&
                (type == MatchType.INTRA_SQUAD || opponentTeamNameState == EditTextState.Available)
} 