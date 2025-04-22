package com.futsalgg.app.presentation.match.matchitem.create

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.match.matchitem.BaseMatchScreen

@Composable
fun CreateMatchScreen(
    navController: NavController,
    viewModel: CreateMatchViewModel = hiltViewModel()
) {

    BaseMatchScreen(
        navController = navController,
        viewModel = viewModel,
        onBottomButtonClick = {
            viewModel.createMatch(
                onSuccess = {
                    navController.navigate(RoutePath.MATCH_RESULT) {
                        popUpTo(route = RoutePath.MAIN)
                    }
                }
            )
        }
    )
}