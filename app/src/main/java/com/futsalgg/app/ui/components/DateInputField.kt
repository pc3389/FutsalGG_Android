package com.futsalgg.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.ui.components.calendar.CalendarBottomSheet
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

@Composable
fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    state: DateState = DateState.Initial,
    messageProvider: (DateState) -> String? = { null },
    canSelectPreviousDate: Boolean = true,
    canSelectFutureDate: Boolean = false
) {
    val yearFocusRequester = remember { FocusRequester() }
    val monthFocusRequester = remember { FocusRequester() }
    val dayFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var showCalendar by remember { mutableStateOf(false) }
    var isYearFocused by remember { mutableStateOf(false) }
    var isMonthFocused by remember { mutableStateOf(false) }
    var isDayFocused by remember { mutableStateOf(false) }
    val messageText = messageProvider(state)

    var year by remember {
        mutableStateOf(
            TextFieldValue(
                value.take(4),
                TextRange(value.take(4).length)
            )
        )
    }
    var month by remember {
        mutableStateOf(
            TextFieldValue(
                value.drop(4).take(2),
                TextRange(value.drop(4).take(2).length)
            )
        )
    }
    var day by remember {
        mutableStateOf(
            TextFieldValue(
                value.drop(6).take(2),
                TextRange(value.drop(6).take(2).length)
            )
        )
    }

    val borderColor = when (state) {
        DateState.Initial -> FutsalggColor.mono500
        DateState.ErrorNotInRange -> FutsalggColor.orange
        DateState.ErrorStyle -> FutsalggColor.orange
        DateState.Available -> FutsalggColor.mono500
    }

    LaunchedEffect(value) {
        if (value.isNotEmpty()) {
            try {
                val date = LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyyMMdd"))
                val yearText = date.year.toString().padStart(4, '0')
                val monthText = date.monthValue.toString().padStart(2, '0')
                val dayText = date.dayOfMonth.toString().padStart(2, '0')
                year = TextFieldValue(
                    yearText,
                    TextRange(yearText.length)
                )
                month = TextFieldValue(
                    monthText,
                    TextRange(monthText.length)
                )
                day = TextFieldValue(
                    dayText,
                    TextRange(dayText.length)
                )
            } catch (e: Exception) {
                // 파싱 실패 시 현재 값 유지
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .clickable {
                    if (day.text.isEmpty()) {
                        if (month.text.isEmpty()) {
                            yearFocusRequester.requestFocus()
                        } else {
                            monthFocusRequester.requestFocus()
                        }
                    } else {
                        dayFocusRequester.requestFocus()
                    }
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(modifier = Modifier.weight(1f)) {
                    DateTextField(
                        value = year,
                        onValueChange = { newYear ->
                            if (newYear.text.length <= 4) {
                                year = newYear
                                if (newYear.text.length == 4) {
                                    monthFocusRequester.requestFocus()
                                }
                            }
                        },
                        width = 48,
                        focusRequester = yearFocusRequester,
                        placeholder = "YYYY",
                        onFocusChanged = { isYearFocused = it.isFocused }
                    )

                    Text(
                        text = "-",
                        style = FutsalggTypography.regular_17_200,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    DateTextField(
                        value = month,
                        onValueChange = { newMonth ->
                            if (newMonth.text.length <= 2) {
                                month = newMonth
                                if (newMonth.text.length == 2) {
                                    dayFocusRequester.requestFocus()
                                } else if (newMonth.text.isEmpty()) {
                                    yearFocusRequester.requestFocus()
                                    year = TextFieldValue(year.text, TextRange(year.text.length))
                                }
                            }
                        },
                        width = 32,
                        focusRequester = monthFocusRequester,
                        placeholder = "MM",
                        onFocusChanged = { isMonthFocused = it.isFocused }
                    )

                    Text(
                        text = "-",
                        style = FutsalggTypography.regular_17_200,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    DateTextField(
                        value = day,
                        onValueChange = { newDay ->
                            if (newDay.text.length <= 2) {
                                day = newDay
                                if (newDay.text.isEmpty()) {
                                    monthFocusRequester.requestFocus()
                                    month = TextFieldValue(month.text, TextRange(month.text.length))
                                }
                            }
                        },
                        width = 32,
                        focusRequester = dayFocusRequester,
                        placeholder = "DD",
                        imeAction = ImeAction.Done,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                            }
                        ),
                        onFocusChanged = { isDayFocused = it.isFocused }
                    )
                }

                IconButton(
                    onClick = {
                        showCalendar = true
                    },
                    modifier = Modifier.padding(start = 8.dp, end = 4.dp)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_calendar_20),
                        contentDescription = "달력 열기",
                    )
                }

                if (showCalendar) {
                    val initialDate = remember(year.text, month.text, day.text) {
                        if (year.text.isEmpty() || month.text.isEmpty() || day.text.isEmpty()) {
                            LocalDate.now()
                        } else {
                            try {
                                LocalDate.of(
                                    year.text.toInt(),
                                    Month.of(month.text.toInt()),
                                    day.text.toInt()
                                )
                            } catch (e: Exception) {
                                LocalDate.now()
                            }
                        }
                    }

                    ShowCalendar(
                        initialDate = initialDate,
                        onConfirm = { date ->
                            try {
                                val yearText = date.year.toString()
                                val monthText = date.monthValue.toString().padStart(2, '0')
                                val dayText = date.dayOfMonth.toString().padStart(2, '0')
                                year = TextFieldValue(
                                    yearText,
                                    TextRange(yearText.length)
                                )
                                month = TextFieldValue(
                                    monthText,
                                    TextRange(monthText.length)
                                )
                                day = TextFieldValue(
                                    dayText,
                                    TextRange(dayText.length)
                                )
                                focusManager.clearFocus()
                            } catch (e: Exception) {
                                // 파싱 실패 시 현재 값 유지
                            }
                            showCalendar = false
                        },
                        onDismissRequest = {
                            showCalendar = false
                        },
                        canSelectPreviousDate = canSelectPreviousDate,
                        canSelectFutureDate = canSelectFutureDate
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = messageText != null,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = messageText ?: "",
                color = borderColor,
                style = FutsalggTypography.regular_17_200,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }

    // focus가 모두 해제되었을 때 onValueChange 호출
    LaunchedEffect(isYearFocused, isMonthFocused, isDayFocused) {
        if ((!isYearFocused && !isMonthFocused && !isDayFocused) && (year.text.isNotEmpty() || month.text.isNotEmpty() || day.text.isNotEmpty())) {
            year = TextFieldValue(
                year.text.padStart(4, '0'),
                TextRange(year.text.padStart(4, '0').length)
            )
            month = TextFieldValue(
                month.text.padStart(2, '0'),
                TextRange(month.text.padStart(2, '0').length)
            )
            day = TextFieldValue(
                day.text.padStart(2, '0'),
                TextRange(day.text.padStart(2, '0').length)
            )
            val newDate = "${year.text}${month.text}${day.text}"
            if (newDate != value) {
                onValueChange(newDate)
            }
        }
    }
}

@Composable
private fun DateTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    width: Int,
    focusRequester: FocusRequester,
    placeholder: String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions? = null,
    onFocusChanged: (FocusState) -> Unit = {}
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = keyboardActions ?: KeyboardActions.Default,
        modifier = Modifier
            .width(width.dp)
            .focusRequester(focusRequester)
            .onFocusChanged(onFocusChanged),
        textStyle = FutsalggTypography.regular_17_200,
        maxLines = 1,
        decorationBox = { innerTextField ->
            if (value.text.isEmpty()) {
                Text(
                    text = placeholder,
                    color = FutsalggColor.mono400,
                    style = FutsalggTypography.regular_17_200
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun ShowCalendar(
    initialDate: LocalDate = LocalDate.now(),
    onConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    canSelectPreviousDate: Boolean = true,
    canSelectFutureDate: Boolean = false
) {
    CalendarBottomSheet(
        initialDate = initialDate,
        onConfirm = onConfirm,
        onDismissRequest = onDismissRequest,
        canSelectPreviousDate = canSelectPreviousDate,
        canSelectFutureDate = canSelectFutureDate
    )
}