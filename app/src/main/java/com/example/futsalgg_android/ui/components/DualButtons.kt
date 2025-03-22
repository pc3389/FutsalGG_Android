package com.example.futsalgg_android.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.futsalgg_android.ui.theme.FutsalggColor

@Composable
fun DualButtons(
    leftText: String,
    rightText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    leftContainerColor: Color = FutsalggColor.white,
    rightContainerColor: Color = FutsalggColor.mono900,
    leftContentColor: Color = FutsalggColor.mono900,
    rightContentColor: Color = FutsalggColor.white,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp
) {

    Row(
        modifier =modifier.fillMaxWidth()
            .padding(
                horizontal = horizontalPadding,
                vertical = verticalPadding
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SingleButton(
            text = leftText,
            onClick = onLeftClick,
            modifier = Modifier.weight(1f),
            containerColor = leftContainerColor,
            contentColor = leftContentColor,
            hasBorder = leftContainerColor == FutsalggColor.white
        )
        SingleButton(
            text = rightText,
            onClick = onRightClick,
            modifier = Modifier.weight(1f),
            containerColor = rightContainerColor,
            contentColor = rightContentColor,
            hasBorder = rightContainerColor == FutsalggColor.white
        )
    }
}

@Preview
@Composable
fun PreviewDualButtons() {
    DualButtons(
        "LeftText",
        "RightText",
        onLeftClick = {},
        onRightClick = {}
    )
}