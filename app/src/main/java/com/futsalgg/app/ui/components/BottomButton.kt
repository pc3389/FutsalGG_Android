package com.futsalgg.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun BottomButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Box(modifier = modifier) {
        HorizontalDivider(
            thickness = 1.dp,
            color = FutsalggColor.mono400
        )

        SingleButton(
            modifier = Modifier.padding(16.dp),
            text = text,
            onClick = onClick,
            enabled = enabled
        )
    }
}