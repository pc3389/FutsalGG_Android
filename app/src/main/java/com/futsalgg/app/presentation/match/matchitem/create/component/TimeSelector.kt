package com.futsalgg.app.presentation.match.matchitem.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.futsalgg.app.presentation.common.state.EditTextState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TimeSelectorItem(
    modifier: Modifier = Modifier,
    knowTime: Boolean = false,
    time: String,
    onSelected: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit,
    timeReady: (Boolean) -> Unit
) {
    val hrFocusRequester = remember { FocusRequester() }
    val minFocusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var isHourFocused by remember { mutableStateOf(false) }
    var isMinFocused by remember { mutableStateOf(false) }
    var textState by remember { mutableStateOf(EditTextState.Initial) }

    val borderColor = when (textState) {
        EditTextState.ErrorCannotUseSpecialChar -> FutsalggColor.orange
        EditTextState.Available -> FutsalggColor.mint500
        else -> FutsalggColor.mono500
    }

    val stateMessage = when (textState) {
        EditTextState.ErrorCannotUseSpecialChar -> "에러 문구"
        EditTextState.Available -> "사용가능 문구"
        else -> ""
    }

    if (isMinFocused || isHourFocused) {
        timeReady(false)
    }


    var hour by remember {
        mutableStateOf(
            TextFieldValue(
                time.take(2),
                TextRange(time.take(2).length)
            )
        )
    }
    var min by remember {
        mutableStateOf(
            TextFieldValue(
                time.drop(4).take(2),
                TextRange(time.drop(4).take(2).length)
            )
        )
    }

    LaunchedEffect(time) {
        if (time.isNotEmpty()) {
            try {
                val date = LocalDate.parse(time, DateTimeFormatter.ofPattern("hhmm"))
                val yearText = date.year.toString().padStart(4, '0')
                val monthText = date.monthValue.toString().padStart(2, '0')
                hour = TextFieldValue(
                    yearText,
                    TextRange(yearText.length)
                )
                min = TextFieldValue(
                    monthText,
                    TextRange(monthText.length)
                )
            } catch (e: Exception) {
                // 파싱 실패 시 현재 값 유지
            }
        }
    }

    // focus가 모두 해제되었을 때 onValueChange 호출
    LaunchedEffect(isHourFocused, isMinFocused) {
        textState = EditTextState.Initial
        if ((!isHourFocused && !isMinFocused) && (hour.text.isNotEmpty() || min.text.isNotEmpty())) {
            hour = TextFieldValue(
                hour.text.padStart(2, '0'),
                TextRange(hour.text.padStart(2, '0').length)
            )
            min = TextFieldValue(
                min.text.padStart(2, '0'),
                TextRange(min.text.padStart(2, '0').length)
            )
            val newDate = "${hour.text}${min.text}"
            if (newDate != time) {
                onTimeChange(newDate)
            }

            if (hour.text.toInt() > 24 || min.text.toInt() > 60) {
                textState = EditTextState.ErrorCannotUseSpecialChar
            } else {
                textState = EditTextState.Available
                timeReady(true)
            }
        }
    }

    Column(modifier = modifier) {
        // 시간 선택 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelected(true) }
                .border(
                    width = 1.dp,
                    color = if (knowTime) FutsalggColor.mono900 else FutsalggColor.mono200,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(
                            id = if (knowTime) R.drawable.ic_checkbox_true_24
                            else R.drawable.ic_checkbox_false_24
                        ),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.create_match_time_known),
                        color = if (knowTime) FutsalggColor.mono900 else FutsalggColor.mono500,
                        style = FutsalggTypography.bold_17_200
                    )
                }

                AnimatedVisibility(
                    visible = knowTime,
                    enter = expandVertically(
                        animationSpec = tween(300)
                    ) + fadeIn(
                        animationSpec = tween(300)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(300)
                    ) + fadeOut(
                        animationSpec = tween(300)
                    )
                ) {
                    // 시간 선택 박스
                    Column(
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .border(
                                    width = 1.dp,
                                    color = borderColor,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    if (hour.text.isEmpty()) {
                                        hrFocusRequester.requestFocus()
                                    } else {
                                        minFocusRequester.requestFocus()
                                    }
                                }
                                .animateContentSize(
                                    animationSpec = tween(300)
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TimeTextField(
                                    value = hour,
                                    onValueChange = { newHour ->
                                        if (newHour.text.length <= 2) {
                                            hour = newHour
                                            if (newHour.text.length == 2) {
                                                minFocusRequester.requestFocus()
                                            }
                                        }
                                    },
                                    width = 24,
                                    focusRequester = hrFocusRequester,
                                    placeholder = "00",
                                    onFocusChanged = { isHourFocused = it.isFocused }
                                )

                                Text(
                                    text = ":",
                                    style = FutsalggTypography.regular_17_200,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )

                                TimeTextField(
                                    value = min,
                                    onValueChange = { newMin ->
                                        if (newMin.text.length <= 2) {
                                            min = newMin
                                            if (newMin.text.isEmpty()) {
                                                hrFocusRequester.requestFocus()
                                                min = TextFieldValue(
                                                    min.text,
                                                    TextRange(min.text.length)
                                                )
                                            }
                                        }
                                    },
                                    width = 24,
                                    focusRequester = minFocusRequester,
                                    placeholder = "00",
                                    imeAction = ImeAction.Done,
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            focusManager.clearFocus()
                                        }
                                    ),
                                    onFocusChanged = { isMinFocused = it.isFocused }
                                )
                            }
                        }
                        if (stateMessage.isNotEmpty()) {
                            Text(
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    start = 32.dp
                                ),
                                text = stateMessage,
                                style = FutsalggTypography.regular_17_200,
                                color = borderColor
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // 미정
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelected(false) }
                .border(
                    width = 1.dp,
                    color = if (knowTime) FutsalggColor.mono200 else FutsalggColor.mono900,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = if (knowTime) R.drawable.ic_checkbox_false_24
                        else R.drawable.ic_checkbox_true_24
                    ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.create_match_time_known),
                    color = if (knowTime) FutsalggColor.mono500 else FutsalggColor.mono900,
                    style = FutsalggTypography.bold_17_200
                )
            }
        }
    }
}

@Composable
private fun TimeTextField(
    modifier: Modifier = Modifier,
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
        modifier = modifier
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