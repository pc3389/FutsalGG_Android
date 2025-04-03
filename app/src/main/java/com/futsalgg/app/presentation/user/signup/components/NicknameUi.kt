package com.futsalgg.app.presentation.user.signup.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun NicknameUi(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    isCheckEnabled: Boolean,
    nicknameState: EditTextState,
    nicknameCheck: () -> Unit,
    context: Context
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.signup_must),
            style = FutsalggTypography.bold_17_200,
            color = FutsalggColor.mint500,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.TopEnd)
        )
        TextWithStar(
            textRes = R.string.signup_nickname,
            modifier = Modifier.padding(top = 40.dp)
        )
    }

    Spacer(Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        EditTextWithState(
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp),
            value = nickname,
            onValueChange = onNicknameChange,
            state = nicknameState,
            messageProvider = { state ->
                when (state) {
                    EditTextState.ErrorCannotUse -> context.getString(R.string.signup_nickname_error_message_cannot_use)
                    EditTextState.ErrorAlreadyExisting -> context.getString(R.string.signup_nickname_error_message_already)
                    EditTextState.Available -> context.getString(R.string.signup_nickname_available)
                    else -> null
                }
            }
        )

        SingleButton(
            text = stringResource(R.string.signup_nickname_ckeck_button),
            onClick = nicknameCheck,
            modifier = Modifier.weight(1f),
            enabled = isCheckEnabled
        )
    }
}