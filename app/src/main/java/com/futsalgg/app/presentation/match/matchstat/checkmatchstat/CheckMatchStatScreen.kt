package com.futsalgg.app.presentation.match.matchstat.checkmatchstat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.matchstat.base.BaseMatchStatScreen
import com.futsalgg.app.presentation.match.matchstat.component.ScoreTemplate
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer20
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.toFullDateFormat

@Composable
fun CheckMatchStatScreen(
    navController: NavController,
    viewModel: CheckMatchStatViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiStateFlow.collectAsState()
    val roundScoreState by viewModel.roundScoreStateFlow.collectAsState()
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
        Column {
            Column(
                modifier = Modifier
                    .background(FutsalggColor.white)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer20()

                Text(
                    text = matchState.matchDate.toFullDateFormat(),
                    style = FutsalggTypography.bold_20_300,
                    color = FutsalggColor.mono900
                )
                VerticalSpacer8()
                Text(
                    text = "${matchState.startTime} ~ ${matchState.endTime}",
                    style = FutsalggTypography.bold_20_300,
                    color = FutsalggColor.mono900
                )
                VerticalSpacer16()
                Text(
                    text = matchState.location,
                    style = FutsalggTypography.light_15_100,
                    color = FutsalggColor.mono900
                )
                VerticalSpacer12()
                HorizontalDivider(
                    thickness = 1.dp,
                    color = FutsalggColor.mono200
                )
            }
            ScoreTemplate(
                roundScoreState, participants
            )
        }
    }
}