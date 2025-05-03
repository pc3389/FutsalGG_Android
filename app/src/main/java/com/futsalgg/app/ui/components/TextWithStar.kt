package com.futsalgg.app.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun TextWithStar(text: String, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            append("$text ")
            withStyle(style = SpanStyle(color = FutsalggColor.mint500)) {
                append("*")
            }
        },
        style = FutsalggTypography.bold_20_300,
        modifier = modifier
    )
}