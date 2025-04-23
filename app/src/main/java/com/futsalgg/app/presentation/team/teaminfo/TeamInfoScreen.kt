package com.futsalgg.app.presentation.team.teaminfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.screen.BaseScreen

@Composable
fun TeamInfoScreen(
    navController: NavController,
    viewModel: TeamInfoViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    BaseScreen(
        navController = navController,
        uiState = uiState,
        title = stringResource(R.string.team_info_title),
        screenName = RoutePath.TEAM_INFO,
        rightText = stringResource(R.string.team_leave_text),
        onRightClick = {
            // TODO Team 탈퇴
        }
    ) {

    }
}