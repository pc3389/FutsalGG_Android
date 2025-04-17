package com.futsalgg.app.presentation.common.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.components.FutsalggTopBar
import com.futsalgg.app.ui.theme.FutsalggColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BaseScreen(
    navController: NavController,
    title: String,
    rightIcon: ImageVector? = null,
    onRightClick: (() -> Unit)? = null,
    uiState: UiState = UiState.Initial,
    content: @Composable (PaddingValues) -> Unit
) {

    val systemUiController = rememberSystemUiController()
    LaunchedEffect(Unit) {
        systemUiController.setSystemBarsColor(
            color = FutsalggColor.white,
            darkIcons = true
        )
    }

    Scaffold(
        modifier = Modifier.background(
            FutsalggColor.white
        )
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            FutsalggTopBar(
                title = title,
                onBackClick = { navController.popBackStack() },
                rightIcon = rightIcon,
                onRightClick = onRightClick
            )
        },
        content = content
    )

    if (uiState is UiState.Loading) {
        LoadingScreen()
    }
}
