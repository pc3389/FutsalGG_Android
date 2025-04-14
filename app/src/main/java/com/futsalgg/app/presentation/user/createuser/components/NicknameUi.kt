package com.futsalgg.app.presentation.user.createuser.components

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.user.util.NicknameContents
import com.futsalgg.app.ui.components.FormRequiredAndHeader

@Composable
fun NicknameUi(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    isCheckEnabled: Boolean,
    nicknameState: EditTextState,
    nicknameCheck: () -> Unit,
    context: Context
) {

    FormRequiredAndHeader(
        headerText = stringResource(R.string.nickname_text)
    )

    Spacer(Modifier.height(8.dp))

    NicknameContents(
        nickname = nickname,
        onNicknameChange = onNicknameChange,
        isCheckEnabled = isCheckEnabled,
        nicknameState = nicknameState,
        nicknameCheck = nicknameCheck,
        context = context
    )
}