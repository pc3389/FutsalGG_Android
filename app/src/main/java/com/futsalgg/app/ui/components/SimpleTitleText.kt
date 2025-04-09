package com.futsalgg.app.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun SimpleTitleText(text: String) {
    Text(
        text = text,
        style = FutsalggTypography.bold_20_300,
        color = FutsalggColor.mono900
    )
}