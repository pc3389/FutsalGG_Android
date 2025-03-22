package com.example.futsalgg_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.futsalgg_android.ui.theme.FutsalggColor

@Composable
fun BottomDoubleButtons(
    leftText: String,
    rightText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleButton(
            text = leftText,
            onClick = onLeftClick,
            modifier = Modifier.weight(1f),
            containerColor = FutsalggColor.white,
            contentColor = FutsalggColor.mono900,
            hasBorder = true
        )
        SingleButton(
            text = rightText,
            onClick = onRightClick,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun PreviewDoubleButtons() {
    BottomDoubleButtons("Left", "Right", {}, {})
}