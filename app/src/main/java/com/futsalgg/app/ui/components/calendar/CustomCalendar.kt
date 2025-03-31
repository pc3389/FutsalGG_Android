package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate? = null,
    onMonthChange: (YearMonth) -> Unit,
    canSelectPreviousDate: Boolean,
    canSelectAfterDate: Boolean,
    onDateSelected: (LocalDate) -> Unit
) {
    val thisYear = YearMonth.now().year
    val yearRange: IntRange = if (canSelectAfterDate) {
        thisYear..thisYear + 5
    } else if (canSelectPreviousDate) {
        1900..thisYear
    } else {
        1900..thisYear+5
    }
    Column(
        modifier = modifier
    ) {
        CalendarTopBar(
            currentMonth = currentMonth,
            onMonthChange = onMonthChange,
            yearRange = yearRange
        )

        Spacer(modifier = Modifier.height(16.dp))

        CalendarWeekHeader()

        Spacer(modifier = Modifier.height(4.dp))

        val days = generateCalendarDates(currentMonth)
        val today = LocalDate.now()

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(days) { date ->
                DayCell(
                    date = date,
                    today = today,
                    selectedDate = selectedDate,
                    primaryColor = FutsalggColor.mint500,
                    canSelectPreviousDate = canSelectPreviousDate,
                    canSelectAfterDate = canSelectAfterDate,
                    onClick = onDateSelected
                )
            }
        }
    }
}

fun generateCalendarDates(yearMonth: YearMonth): List<LocalDate> {
    val firstDayOfMonth = yearMonth.atDay(1)
    val daysInMonth = yearMonth.lengthOfMonth()
    val dayOfWeekOffset = (firstDayOfMonth.dayOfWeek.value % 7) // Sunday = 0

    val days = mutableListOf<LocalDate>()

    // Add previous month's tail days to align the first day
    for (i in 0 until dayOfWeekOffset) {
        days.add(firstDayOfMonth.minusDays(dayOfWeekOffset.toLong() - i))
    }

    // Add current month's days
    for (i in 1..daysInMonth) {
        days.add(yearMonth.atDay(i))
    }

    return days
}
