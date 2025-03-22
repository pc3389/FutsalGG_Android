package com.example.futsalgg_android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ButtonSingleBottom(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    hasHorizontalPadding: Boolean = true,
    hasVerticalPadding: Boolean = true
) {
    val finalModifier = modifier.then(
        Modifier.padding(
            horizontal = if (hasHorizontalPadding) 16.dp else 0.dp,
            vertical = if (hasVerticalPadding) 16.dp else 0.dp
        )
    )

    SingleButton(
        text = text,
        onClick = onClick,
        modifier = finalModifier
    )
}

@Preview
@Composable
fun ButtonPreview() {
    ButtonSingleBottom("Text", {})
}