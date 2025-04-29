package com.futsalgg.app.presentation.match

import androidx.lifecycle.ViewModel
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.presentation.match.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchSharedViewModel @Inject constructor() : ViewModel() {

    private val _matchParticipantsState = MutableStateFlow<List<MatchParticipantState>>(emptyList())
    val matchParticipantsState: StateFlow<List<MatchParticipantState>> =
        _matchParticipantsState.asStateFlow()

    private val _matchState = MutableStateFlow(Match())
    val matchState: StateFlow<Match> = _matchState.asStateFlow()

    fun updateMatchParticipantsState(newList: List<MatchParticipantState>) {
        _matchParticipantsState.value = newList
    }

    fun updateParticipantSubteam(index: Int) {
        val currentList = _matchParticipantsState.value.toMutableList()
        if (index in currentList.indices) {
            currentList[index] =
                currentList[index].copy(
                    subTeam = if (currentList[index].subTeam == MatchParticipantState.SubTeam.A) {
                        MatchParticipantState.SubTeam.B
                    } else MatchParticipantState.SubTeam.A
                )
            _matchParticipantsState.value = currentList
        }
    }

    fun updateMatch(match: Match) {
        _matchState.value = match
    }
}