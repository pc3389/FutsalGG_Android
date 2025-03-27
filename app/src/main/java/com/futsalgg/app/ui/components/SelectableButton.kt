package com.futsalgg.app.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

/**
 * 선택 가능한 버튼.
 * isSelected가 true일 시 selectedContainerColor & selectedContentColor 적용
 * false일 시 unSelectedContainerColor & unSelectedContentColor 적용
 */
@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    selectedContainerColor: Color = FutsalggColor.mono900,
    selectedContentColor: Color = FutsalggColor.white,
    unSelectedContainerColor: Color = FutsalggColor.white,
    unSelectedContentColor: Color = FutsalggColor.mono900,
    textStyle: TextStyle = FutsalggTypography.bold_17_200,
    hasIcon: Boolean = false,
    @DrawableRes icon: Int = R.drawable.ic_add_14
) {
    val containerColor = if (isSelected) selectedContainerColor else unSelectedContainerColor
    val contentColor = if (isSelected) selectedContentColor else unSelectedContentColor
    val hasBorder = containerColor == FutsalggColor.white

    SingleButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        textStyle = textStyle,
        hasBorder = hasBorder,
        hasIcon = hasIcon,
        icon = icon
    )
}

@Preview
@Composable
fun PreviewSelectableButton() {
    SelectableButton(
        text = "Text",
        isSelected = false,
        onClick = {},
        hasIcon = true
    )
}