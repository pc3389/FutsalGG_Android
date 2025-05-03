package com.futsalgg.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor

/**
 * 두개의 버튼
 * 기본으로 하얀색, 까만색 버튼으로 이루어져 있다.
 *
 * @param leftText left text
 * @param rightText right text
 * @param onLeftClick onClick with left button
 * @param onRightClick onClick with right button
 * @param isLeftBlack It's black and white buttons, left black or White when false
 * @param modifier modifier
 * @param horizontalPadding horizontal padding
 * @param verticalPadding vertical padding
 */
@Composable
fun DoubleButtons(
    leftText: String,
    rightText: String,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    isLeftBlack: Boolean = false,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp
) {

    val white = FutsalggColor.white
    val black = FutsalggColor.mono900
    val leftContainerColor: Color = if (isLeftBlack) black else white
    val rightContainerColor: Color = if (isLeftBlack) white else black
    val leftContentColor: Color = rightContainerColor
    val rightContentColor: Color = leftContainerColor

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