package com.futsalgg.app.presentation.match.matchitem

import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.match.model.Match

data class MatchState(
    val match: Match = Match(),
    val matchDateState: DateState = DateState.Initial,
    val knowsStartTime: Boolean = false,
    val knowsEndTime: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = matchDateState == DateState.Available &&
                match.location.isNotEmpty()

} 