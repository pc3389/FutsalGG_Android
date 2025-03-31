package com.futsalgg.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun <T : Enum<T>> DoubleRadioButtonsEnum(
    selected: T,
    option1: T,
    option2: T,
    onSelect: (T) -> Unit,
    label1: String,
    label2: String,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 0.dp,
    verticalPadding: Dp = 0.dp
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val selectedColor = FutsalggColor.mono900
        val unselectedColor = FutsalggColor.white
        val selectedTextColor = FutsalggColor.white
        val unselectedTextColor = FutsalggColor.mono900

        listOf(option1 to label1, option2 to label2).forEach { (option, label) ->
            val isSelected = selected == option
            SingleButton(
                text = label,
                onClick = { onSelect(option) },
                modifier = Modifier.weight(1f),
                containerColor = if (isSelected) selectedColor else unselectedColor,
                contentColor = if (isSelected) selectedTextColor else unselectedTextColor,
                hasBorder = !isSelected
            )
        }
    }
}