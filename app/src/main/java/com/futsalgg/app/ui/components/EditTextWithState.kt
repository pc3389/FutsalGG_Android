package com.futsalgg.app.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun EditTextWithState(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes hint: Int = R.string.signup_nickname_hint,
    state: EditTextState = EditTextState.Default,
    messageProvider: (EditTextState) -> String? = { null },
    trailingIcon: ImageVector? = null,
    showTrailingIcon: Boolean = false,
    onTrailingIconClick: (() -> Unit)? = null,
    isNumeric: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val borderColor = when (state) {
        EditTextState.Default -> FutsalggColor.mono500
        EditTextState.ErrorCannotUse,
        EditTextState.ErrorAlreadyExisting -> FutsalggColor.orange
        EditTextState.Available -> FutsalggColor.mint500
    }

    val messageText = messageProvider(state)

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    if (!isNumeric || it.all { c -> c.isDigit() }) {
                        onValueChange(it)
                    }
                },
                singleLine = true,
                textStyle = FutsalggTypography.regular_17_200.copy(
                    color = FutsalggColor.mono900
                ),
                cursorBrush = SolidColor(FutsalggColor.mint500),
                visualTransformation = visualTransformation,
                keyboardOptions = if (isNumeric) {
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                } else {
                    KeyboardOptions.Default
                },
                modifier = Modifier
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = stringResource(hint),
                            color = FutsalggColor.mono400,
                            style = FutsalggTypography.regular_17_200
                        )
                    }
                    innerTextField()
                }
            )

            if (showTrailingIcon && onTrailingIconClick != null) {
                trailingIcon?.let {
                    IconButton(onClick = onTrailingIconClick,
                        modifier = Modifier.align(Alignment.CenterEnd)) {
                        Icon(
                            imageVector = it,
                            contentDescription = "날짜 선택",
                            tint = FutsalggColor.mono500
                        )
                    }
                }
            }
        }

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
    val context = LocalContext.current
    EditTextWithState(
        value = "텍스트 테스팅",
//        value = "",
        onValueChange = {},
        state = EditTextState.ErrorCannotUse,
        messageProvider = { st ->
            when (st) {
                EditTextState.ErrorCannotUse -> context.getString(R.string.signup_nickname_error_message_cannot_use)
                EditTextState.ErrorAlreadyExisting -> context.getString(R.string.signup_nickname_error_message_already)
                EditTextState.Available -> context.getString(R.string.signup_nickname_available)
                else -> null
            }
        }
    )
}
