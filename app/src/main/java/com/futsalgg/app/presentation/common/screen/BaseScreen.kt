package com.futsalgg.app.presentation.common.screen

import android.util.Log
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
import com.futsalgg.app.presentation.common.error.UiError
import com.futsalgg.app.presentation.common.error.getCause
import com.futsalgg.app.presentation.common.error.getCode
import com.futsalgg.app.presentation.common.error.getMessage
import com.futsalgg.app.presentation.common.error.getType
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.ui.components.FutsalggTopBar
import com.futsalgg.app.ui.theme.FutsalggColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun BaseScreen(
    navController: NavController,
    screenName: String,
    title: String,
    showMenu: Boolean = false,
    onLeftIconClick: () -> Unit = { navController.popBackStack() },
    rightIcon: ImageVector? = null,
    rightText: String? = null,
    onRightClick: (() -> Unit)? = null,
    uiState: UiState,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .background(
                FutsalggColor.white
            )
            .windowInsetsPadding(WindowInsets.systemBars),
        topBar = {
            FutsalggTopBar(
                title = title,
                onLeftIconClick = { onLeftIconClick() },
                rightIcon = rightIcon,
                onRightClick = onRightClick,
                showMenu = showMenu,
                rightText = rightText
            )
        },
        content = content
    )

    if (uiState is UiState.Loading) {
        LoadingScreen()
    }

    if (uiState is UiState.Error) {
        Log.e(screenName, "${uiState.error.getType()} - [${uiState.error.getCode()}] ${uiState.error.getMessage()} ${uiState.error.getCause()}")
    }
}