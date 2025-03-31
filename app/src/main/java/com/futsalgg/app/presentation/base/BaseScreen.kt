package com.futsalgg.app.presentation.base

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.futsalgg.app.ui.components.FutsalggTopBar
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun BaseScreen(
    navController: NavController,
    @StringRes title: Int,
    @DrawableRes rightIcon: Int? = null,
    onRightClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.background(
            FutsalggColor.white
        ),
        topBar = {
            FutsalggTopBar(
                title = stringResource(title),
                onBackClick = { navController.popBackStack() },
                rightIcon = rightIcon,
                onRightClick = onRightClick
            )
        },
        content = content
    )
}
