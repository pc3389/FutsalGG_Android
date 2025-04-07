package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.theme.FutsalggColor
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBottomSheet(
    initialDate: LocalDate = LocalDate.now(),
    onConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    canSelectPreviousDate: Boolean = true,
    canSelectFutureDate: Boolean = false
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(initialDate) }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        CalendarBottomSheetContent(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onMonthChange = { currentMonth = it },
            onDateSelected = { selectedDate = it },
            canSelectPreviousDate = canSelectPreviousDate,
            canSelectFutureDate = canSelectFutureDate,
            onConfirm = {
                onConfirm(selectedDate)
                onDismissRequest()
            },
        )
    }
}

@Composable
fun CalendarBottomSheetContent(
    currentMonth: YearMonth,
    selectedDate: LocalDate,
    onMonthChange: (YearMonth) -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    canSelectPreviousDate: Boolean,
    canSelectFutureDate: Boolean,
    onConfirm: () -> Unit
) {
    var isDateSelected by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
            .padding(bottom = 24.dp)
    ) {
        CustomCalendar(
            currentMonth = currentMonth,
            selectedDate = selectedDate,
            onMonthChange = onMonthChange,
            canSelectPreviousDate = canSelectPreviousDate,
            canSelectFutureDate = canSelectFutureDate,
            onDateSelected = {
                onDateSelected(it)
                isDateSelected = true
            }
        )
    }

    HorizontalDivider(
        thickness = 1.dp,
        color = FutsalggColor.mono200
    )
    
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        SingleButton(
            text = stringResource(R.string.select),
            onClick = onConfirm,
            enabled = isDateSelected
        )
    }
}