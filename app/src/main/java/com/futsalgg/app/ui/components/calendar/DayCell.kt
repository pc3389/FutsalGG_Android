package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import java.time.LocalDate

@Composable
fun DayCell(
    date: LocalDate,
    today: LocalDate,
    selectedDate: LocalDate?,
    primaryColor: Color = FutsalggColor.mint500,
    canSelectPreviousDate: Boolean,
    canSelectAfterDate: Boolean,
    onClick: (LocalDate) -> Unit
) {
    val isToday = date == today
    val isSelected = date == selectedDate
    val isPast = date.isBefore(today)
    val isFuture = date.isAfter(today)

    val bgColor = if (isToday) primaryColor else Color.Transparent
    val textColor = if ((!canSelectPreviousDate && isPast) || (!canSelectAfterDate && isFuture)) {
        FutsalggColor.mono200
    } else {
        if (isToday) {
            FutsalggColor.white
        } else if (isSelected) {
            primaryColor
        } else {
            FutsalggColor.mono900
        }
    }

    val textStyle = if (isToday || isSelected) {
        FutsalggTypography.bold_17_200
    } else {
        FutsalggTypography.regular_15_100
    }
    val border = if (isSelected) BorderStroke(1.dp, primaryColor) else null

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(40.dp)
            .background(color = bgColor, shape = RoundedCornerShape(8.dp))
            .then(
                if (border != null) Modifier.border(
                    border,
                    RoundedCornerShape(8.dp)
                ) else Modifier
            )
            .clickable(
                enabled = (!((!canSelectPreviousDate && isPast) || (!canSelectAfterDate && isFuture)))
            ) {
                onClick(date)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
            text = date.dayOfMonth.toString(),
            style = textStyle,
            color = textColor
        )
    }
}
