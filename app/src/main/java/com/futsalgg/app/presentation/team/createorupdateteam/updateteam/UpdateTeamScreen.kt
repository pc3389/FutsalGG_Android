package com.futsalgg.app.presentation.team.createorupdateteam.updateteam

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.team.createorupdateteam.BaseTeamModifyScreen
import com.futsalgg.app.presentation.team.createorupdateteam.ModifyTeamViewModel

@Composable
fun UpdateTeamScreen(
    navController: NavController,
    viewModel: ModifyTeamViewModel = hiltViewModel(),
) {
    BaseTeamModifyScreen(
        navController = navController,
        viewModel = viewModel,
        viewModelType = RoutePath.UPDATE_TEAM
    )
    {
        viewModel.updateTeam {
            navController.navigate(RoutePath.MAIN)
        }
    }
}