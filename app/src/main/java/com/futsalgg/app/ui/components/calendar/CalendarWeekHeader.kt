package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import java.time.DayOfWeek

@Composable
fun CalendarWeekHeader() {
    val daysOfWeek = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY
    )

    Row(modifier = Modifier.fillMaxWidth()) {
        daysOfWeek.forEach { dayOfWeek ->
            val label = dayOfWeek.name.take(3)
            val isSunday = dayOfWeek == DayOfWeek.SUNDAY

            Text(
                text = label,
                modifier = Modifier.weight(1f),
                color = if (isSunday) FutsalggColor.orange else FutsalggColor.mono500,
                style = FutsalggTypography.regular_15_100,
                textAlign = TextAlign.Center
            )
        }
    }
}