package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun CustomCalendar(
    modifier: Modifier = Modifier,
    currentMonth: YearMonth,
    selectedDate: LocalDate? = null,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onMonthTitleClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .padding(top = 8.dp)
    ) {
        CalendarTopBar(
            currentMonth = currentMonth,
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
            onMonthTitleClick = onMonthTitleClick
        )

        Spacer(modifier = Modifier.height(16.dp))

        CalendarWeekHeader()

        Spacer(modifier = Modifier.height(8.dp))

        val days = generateCalendarDates(currentMonth)
        val today = LocalDate.now()

        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(days) { date ->
                DayCell(
                    date = date,
                    today = today,
                    selectedDate = selectedDate,
                    primaryColor = FutsalggColor.mint500,
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


@Preview
@Composable
fun PreviewCustomCalendar() {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    CustomCalendar(
        currentMonth = currentMonth,
        onPreviousMonth = { currentMonth = currentMonth.minusMonths(1) },
        onNextMonth = { currentMonth = currentMonth.plusMonths(1) },
        onMonthTitleClick = {},
        onDateSelected = {}
    )
}
