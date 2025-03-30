package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.SingleButton
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    initialDate: LocalDate = LocalDate.now(),
    onConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(initialDate) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        CalendarBottomSheetContent(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onMonthChange = { currentMonth = it },
            onDateSelected = { selectedDate = it },
            onConfirm = {
                onConfirm(selectedDate)
                onDismissRequest()
            }
        )
    }
}

@Composable
fun CalendarBottomSheetContent(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onMonthChange: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
            .padding(bottom = 48.dp)
    ) {
        CustomCalendar(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onPreviousMonth = { onMonthChange(currentMonth.minusMonths(1)) },
            onNextMonth = { onMonthChange(currentMonth.plusMonths(1)) },
            onMonthTitleClick = { /* TODO */ },
            onDateSelected = onDateSelected
        )

        Spacer(modifier = Modifier.height(16.dp))

        SingleButton(
            text = stringResource(R.string.select),
            onClick = onConfirm
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarBottomSheet() {
    CalendarBottomSheetContent(
        currentMonth = YearMonth.now(),
        selectedDate = LocalDate.now(),
        onMonthChange = {},
        onDateSelected = {},
        onConfirm = {}
    )
}