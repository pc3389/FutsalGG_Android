package com.futsalgg.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun EditTextWithState(
    // TODO Remove stubs
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "사용하실 닉네임을 입력해주세요.",
    state: EditTextState = EditTextState.Default,
    errorCannotUseMessage: String = "영어, 특수 문자는 사용 불가합니다.",
    errorAlreadyExistingMessage: String = "이미 존재하는 닉네임입니다",
    successMessage: String = "사용 가능한 닉네임입니다"
) {
    val borderColor = when (state) {
        EditTextState.Default -> FutsalggColor.mono500
        EditTextState.ErrorCannotUse -> FutsalggColor.orange
        EditTextState.ErrorAlreadyExisting -> FutsalggColor.orange
        EditTextState.Available -> FutsalggColor.mint500
    }

    val messageText = when (state) {
        EditTextState.ErrorCannotUse -> errorCannotUseMessage
        EditTextState.ErrorAlreadyExisting -> errorAlreadyExistingMessage
        EditTextState.Available -> successMessage
        else -> null
    }

    val isError =
        (state == EditTextState.ErrorAlreadyExisting || state == EditTextState.ErrorCannotUse)

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            placeholder = { Text(text = hint) },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            textStyle = FutsalggTypography.regular_17_200,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                errorBorderColor = borderColor,
                cursorColor = FutsalggColor.mint500
            )
        )

        if (messageText != null) {
            Text(
                text = messageText,
                color = borderColor,
                style = FutsalggTypography.regular_17_200,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewEditTextWithState() {
    EditTextWithState(
        value = "나나나나텍스트",
        onValueChange = {},
        state = EditTextState.ErrorCannotUse
    )
}
