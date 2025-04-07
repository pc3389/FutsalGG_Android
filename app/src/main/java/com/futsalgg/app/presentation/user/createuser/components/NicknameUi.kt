package com.futsalgg.app.presentation.user.createuser.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.FormRequiredAndHeader
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
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    // 메시지 프로바이더를 remember로 최적화
    val messageProvider = remember {
        { state: EditTextState ->
            when (state) {
                EditTextState.ErrorCannotUse -> context.getString(R.string.signup_nickname_error_message_cannot_use)
                EditTextState.ErrorAlreadyExisting -> context.getString(R.string.signup_nickname_error_message_already)
                EditTextState.Available -> context.getString(R.string.signup_nickname_available)
                else -> null
            }
        }
    }

    FormRequiredAndHeader(
        headerText = stringResource(R.string.signup_nickname)
    )

    Spacer(Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        EditTextWithState(
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp)
                .focusRequester(focusRequester),
            value = nickname,
            onValueChange = onNicknameChange,
            state = nicknameState,
            messageProvider = messageProvider,
            imeAction = ImeAction.Done,
            onImeAction = {
                if (isCheckEnabled) {
                    nicknameCheck()
                }
                focusManager.clearFocus()
            },
            singleLine = true,
            maxLines = 1
        )

        SingleButton(
            text = stringResource(R.string.check_duplication),
            onClick = {
                nicknameCheck()
                focusManager.clearFocus()
            },
            modifier = Modifier.weight(1f),
            enabled = isCheckEnabled
        )
    }
}