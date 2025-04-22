package com.futsalgg.app.presentation.match.matchstat.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun BaseMatchStatScreen(
    navController: NavController,
    screenName: String,
    menuClick: () -> Unit,
    uiState: UiState,
    viewModel: MatchStatBaseViewModel,
    contents: @Composable () -> Unit
) {
    val matchState by viewModel.matchState.collectAsState()
    BaseScreen(
        navController = navController,
        screenName = screenName,
        title = matchState.type.toString(),
        showMenu = true,
        onLeftIconClick = menuClick,
        rightIcon = ImageVector.vectorResource(R.drawable.ic_close_24),
        onRightClick = { navController.popBackStack() },
        uiState = uiState
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(
                Modifier.fillMaxSize()
            ) {
                Box(
                    Modifier.weight(1f)
                        .fillMaxHeight()
                        .background(FutsalggColor.mono900)
                )
                Box(
                    Modifier.weight(1f)
                        .fillMaxHeight()
                        .background(FutsalggColor.mint200)
                )
            }

            Box(
                Modifier.fillMaxSize()
                    .align(Alignment.TopCenter)
            ) {
                contents()
            }
        }
    }
}