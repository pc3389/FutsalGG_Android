package com.futsalgg.app.ui.components.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R

data class IconState(
    val iconRes: Int = R.drawable.ic_arrow_forward_16,
    val isIconLocationStart: Boolean = false,
    val iconPaddingFromText: Dp = 18.dp,
    val onClick: () -> Unit = {}
)