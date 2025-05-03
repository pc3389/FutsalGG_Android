package com.futsalgg.app.presentation.team.createorupdateteam.createteam

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.team.createorupdateteam.BaseTeamModifyScreen
import com.futsalgg.app.presentation.team.createorupdateteam.ModifyTeamViewModel

@Composable
fun CreateTeamScreen(
    navController: NavController,
    viewModel: ModifyTeamViewModel = hiltViewModel()
) {
    BaseTeamModifyScreen(
        navController = navController,
        viewModel = viewModel
    ) {
        viewModel.createTeam {
            navController.navigate(RoutePath.MAIN)
        }
    }
}