package com.futsalgg.app.presentation.match.matchitem.update

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.match.matchitem.BaseMatchScreen

@Composable
fun UpdateMatchScreen(
    navController: NavController,
    viewModel: UpdateMatchViewModel = hiltViewModel()
) {
    BaseMatchScreen(
        navController = navController,
        viewModel = viewModel,
        onBottomButtonClick = {
            viewModel.updateMatch(
                onSuccess = {
                    navController.popBackStack(
                        route = RoutePath.MATCH_RESULT,
                        inclusive = false
                    )
                }
            )
        },
        titleText = stringResource(R.string.update_match_title),
        buttonText = stringResource(R.string.update_text)
    )
}