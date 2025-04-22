package com.futsalgg.app.presentation.match.matchitem

import androidx.lifecycle.ViewModel
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.util.isValidDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

open class BaseMatchViewModel: ViewModel() {
    protected val uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> = uiState.asStateFlow()

    protected val matchState = MutableStateFlow(MatchState())
    val matchStateFlow: StateFlow<MatchState> = matchState.asStateFlow()

    internal fun onLocationChange(newValue: String) {
        matchState.value = matchState.value.copy(
            match = matchState.value.match.copy(
                location = newValue
            )
        )
    }

    internal fun onStartTimeChange(newValue: String) {
        matchState.value = matchState.value.copy(
            match = matchState.value.match.copy(
                startTime = newValue
            )
        )
    }

    internal fun onKnowsStartTimeChange(newValue: Boolean) {
        val state = matchState.value
        matchState.value = state.copy(
            knowsStartTime = newValue
        )
    }

    internal fun updateStartTimeReady(newValue: Boolean) {
        val state = matchState.value
        matchState.value = state.copy(
            startTimeReady = newValue
        )
    }

    internal fun onEndTimeChange(newValue: String) {
        matchState.value = matchState.value.copy(
            match = matchState.value.match.copy(
                endTime = newValue
            )
        )
    }

    internal fun updateEndTimeReady(newValue: Boolean) {
        val state = matchState.value
        matchState.value = state.copy(
            endTimeReady = newValue
        )
    }

    internal fun onKnowsEndTimeChange(newValue: Boolean) {
        val state = matchState.value
        matchState.value = state.copy(
            knowsEndTime = newValue
        )
    }

    internal fun onOpponentTeamNameChange(newValue: String) {
        matchState.value = matchState.value.copy(
            match = matchState.value.match.copy(
                opponentTeamName = newValue
            )
        )
    }

    internal fun onValidateMatchDate(value: String) {
        matchState.value = matchState.value.copy(
            match = matchState.value.match.copy(
                matchDate = value
            ),
            matchDateState = if (value.isEmpty()) {
                DateState.Initial
            } else isValidDate(value,
                canNotFuture = false)
        )
    }

    internal fun updateUiState(value: UiState) {
        uiState.value = value
    }
}