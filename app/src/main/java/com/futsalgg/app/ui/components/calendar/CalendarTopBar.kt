package com.futsalgg.app.ui.components.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.futsalgg.app.R
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@Composable
fun CalendarTopBar(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit,
    yearRange: IntRange
) {
    // 다이얼로그 표시 여부 상태
    var showPicker by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { onMonthChange(currentMonth.minusMonths(1)) }) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_arrow_backward_small
                ), contentDescription = "Previous Month"
            )
        }
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("yyyy년 M월")),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                showPicker = true
            }
        )
        IconButton(onClick = { onMonthChange(currentMonth.plusMonths(1)) }) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    R.drawable.ic_arrow_forward_small
                ), contentDescription = "Next Month"
            )
        }
    }

    if (showPicker) {
        YearMonthPickerDialog(
            initialYearMonth = currentMonth,
            onDismiss = { showPicker = false },
            onConfirm = { newYearMonth ->
                onMonthChange(newYearMonth)
                showPicker = false
            },
            yearRange = yearRange
        )
    }
}

@Composable
fun YearDropdown(
    yearRange: IntRange,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selectedYear.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Year") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Year",
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            yearRange.forEach { year ->
                DropdownMenuItem(
                    text = { Text(year.toString()) },
                    onClick = {
                        onYearSelected(year)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun MonthDropdown(
    selectedMonth: Int,
    onMonthSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedTextField(
            value = selectedMonth.toString(),
            onValueChange = {},
            readOnly = true,
            label = { Text("Month") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Month",
                    modifier = Modifier.clickable { expanded = true }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            (1..12).forEach { month ->
                DropdownMenuItem(
                    text = { Text(month.toString()) },
                    onClick = {
                        onMonthSelected(month)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun YearMonthPickerDialog(
    initialYearMonth: YearMonth,
    onDismiss: () -> Unit,
    onConfirm: (YearMonth) -> Unit,
    yearRange: IntRange = 1900..YearMonth.now().year
) {
    var selectedYear by remember { mutableIntStateOf(initialYearMonth.year) }
    var selectedMonth by remember { mutableIntStateOf(initialYearMonth.monthValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Year and Month") },
        text = {
            Column {
                YearDropdown(
                    yearRange = yearRange,
                    selectedYear = selectedYear,
                    onYearSelected = { selectedYear = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                MonthDropdown(
                    selectedMonth = selectedMonth,
                    onMonthSelected = { selectedMonth = it }
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(YearMonth.of(selectedYear, selectedMonth)) }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}