package com.futsalgg.app.presentation.signup.components

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.calendar.CalendarBottomSheet
import com.futsalgg.app.util.DateTransformation
import com.futsalgg.app.util.toLocalDateOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun BirthdayUi(
    context: Context,
    birthday: String,
    onBirthdayChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    showCalendarSheet: Boolean,
    onCalendarConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit,
    birthdayState: EditTextState,
    onValidateBirthday: () -> Unit
) {
    TextWithStar(textRes = R.string.signup_birthday)
    Spacer(Modifier.height(8.dp))

    EditTextWithState(
        value = birthday,
        onValueChange = onBirthdayChange,
        modifier = Modifier.fillMaxWidth(),
        hint = R.string.signup_birthday_hint, // YYYY-MM-DD
        state = birthdayState,
        trailingIcon = androidx.compose.ui.graphics.vector.ImageVector.vectorResource(R.drawable.ic_calendar_20),
        showTrailingIcon = true,
        onTrailingIconClick = onCalendarClick,
        isNumeric = true,
        visualTransformation = DateTransformation(),
        messageProvider = { state ->
            when (state) {
                EditTextState.ErrorCannotUse -> context.getString(R.string.signup_birthday_error_invalid)
                else -> null
            }
        },
        onFocusChanged = { isFocused ->
            // 포커스가 사라질 때 유효성 검사 수행
            if (birthday.isNotEmpty() && !isFocused) {
                onValidateBirthday()
            }
        }
    )

    if (showCalendarSheet) {
        CalendarBottomSheet(
            initialDate = birthday.toLocalDateOrNull() ?: LocalDate.now(),
            onConfirm = onCalendarConfirm,
            onDismissRequest = onDismissRequest,
            canSelectPreviousDate = true
        )
    }
}

/**
 * 주어진 생년월일 문자열이 지정된 날짜 포맷에 맞고,
 * 오늘 날짜 이전이며 1900년 1월 1일 이후(또는 같은 날짜)인지 확인합니다.
 *
 * @param birthday 검증할 생년월일 문자열
 * @param pattern 날짜 포맷 (기본값: "yyyyMMdd"; 필요에 따라 "yyyy-MM-dd" 등으로 변경 가능)
 * @return 조건에 맞으면 true, 아니면 false
 */
fun isValidBirthday(birthday: String, pattern: String = "yyyyMMdd"): Boolean {
    return try {
        val parsedDate = LocalDate.parse(birthday, DateTimeFormatter.ofPattern(pattern))
        val earliest = LocalDate.of(1900, 1, 1)
        val today = LocalDate.now()
        // parsedDate가 1900-01-01보다 이전이면 안 되고, 오늘보다 이후이면 안 됩니다.
        // 여기서는 1900-01-01은 유효하며, 오늘 날짜는 아직 도달하지 않은 경우로 가정합니다.
        !parsedDate.isBefore(earliest) && parsedDate.isBefore(today)
    } catch (e: DateTimeParseException) {
        false
    }
}