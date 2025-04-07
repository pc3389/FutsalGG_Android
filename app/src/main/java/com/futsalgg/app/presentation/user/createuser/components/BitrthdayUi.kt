package com.futsalgg.app.presentation.user.createuser.components

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.ui.components.DateInputField
import com.futsalgg.app.ui.components.TextWithStar

@Composable
fun BirthdayUi(
    context: Context,
    birthday: String,
    onBirthdayChange: (String) -> Unit,
    birthdayState: DateState,
) {
    val messageProvider = remember {
        { state: DateState ->
            when (state) {
                DateState.ErrorNotInRange -> context.getString(R.string.date_error_future_date_not_available)
                DateState.ErrorStyle -> context.getString(R.string.date_error_wrong_date_format)
                else -> null
            }
        }
    }

    TextWithStar(text = stringResource(R.string.signup_birthday))
    Spacer(Modifier.height(8.dp))

    DateInputField(
        value = birthday,
        onValueChange = onBirthdayChange,
        modifier = Modifier.fillMaxWidth(),
        state = birthdayState,
        messageProvider = messageProvider
    )
}