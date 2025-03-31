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
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.calendar.CalendarBottomSheet
import com.futsalgg.app.util.DateTransformation
import com.futsalgg.app.util.toLocalDateOrNull
import java.time.LocalDate

@Composable
fun BirthdayUi(
    context: Context,
    birthday: String,
    onBirthdayChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    showCalendarSheet: Boolean,
    onCalendarConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    TextWithStar(textRes = R.string.signup_birthday)
    Spacer(Modifier.height(8.dp))

    EditTextWithState(
        value = birthday,
        onValueChange = onBirthdayChange,
        modifier = Modifier.fillMaxWidth(),
        hint = R.string.signup_birthday_hint, // YYYY-MM-DD
        state = EditTextState.Default,
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
        }
    )

    if (showCalendarSheet) {
        CalendarBottomSheet(
            initialDate = birthday.toLocalDateOrNull() ?: LocalDate.now(),
            onConfirm = onCalendarConfirm,
            onDismissRequest = onDismissRequest
        )
    }
}