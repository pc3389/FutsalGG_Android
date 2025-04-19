package com.futsalgg.app.presentation.match.matchstat.updatematchstat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchstat.base.BaseMatchStatScreen
import com.futsalgg.app.presentation.match.matchstat.component.ScoreTemplate

@Composable
fun UpdateMatchStatScreen(
    navController: NavController,
    viewModel: UpdateMatchStatViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiStateFlow.collectAsState()
    val roundScoreState by viewModel.tempMatchStatsStateFlow.collectAsState()
    val participants by viewModel.participantsStateFlow.collectAsState()
    val matchState by sharedViewModel.matchState.collectAsState()

    BaseMatchStatScreen(
        navController = navController,
        sharedViewModel = sharedViewModel,
        menuClick = {
            // TODO Menu Click
        },
        uiState = uiState
    ) {
        ScoreTemplate(
            roundStatsList = roundScoreState,
            participants = participants,
            isEditable = true,
            onGoalClick = { roundIndex, teamIndex ->
                viewModel.addGoal(
                    roundIndex = roundIndex,
                    teamIndex = teamIndex,
                    matchParticipantId = ""
                )
            },
            onAssistClick = { roundIndex, teamIndex, goalIndex ->
                viewModel.addAssist(
                    roundIndex = roundIndex,
                    teamIndex = teamIndex,
                    goalIndex,
                    matchParticipantId = ""
                )
            }
        )
    }
}