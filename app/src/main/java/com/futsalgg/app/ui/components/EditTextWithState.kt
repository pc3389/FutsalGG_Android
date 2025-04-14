package com.futsalgg.app.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun EditTextWithState(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = stringResource(R.string.nickname_hint),
    state: EditTextState = EditTextState.Initial,
    messageProvider: (EditTextState) -> String? = { null },
    trailingIcon: ImageVector? = null,
    showTrailingIcon: Boolean = false,
    onTrailingIconClick: (() -> Unit)? = null,
    isNumeric: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onFocusChanged: (Boolean) -> Unit = {},
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    singleLine: Boolean = true,
    maxLines: Int = 1,
    maxLength: Int? = null
) {
    //TODO MaxLength!!
    val borderColor = when (state) {
        EditTextState.Initial -> FutsalggColor.mono500
        EditTextState.Default -> FutsalggColor.mono500
        EditTextState.ErrorCannotUse,
        EditTextState.ErrorAlreadyExisting -> FutsalggColor.orange
        EditTextState.Available -> FutsalggColor.mint500
    }

    val messageText = messageProvider(state)

    val focusRequester = remember { androidx.compose.ui.focus.FocusRequester() }

    Box(modifier = modifier.fillMaxWidth()
        .height(78.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .padding(horizontal = 16.dp)
                .clickable { focusRequester.requestFocus() },
            contentAlignment = Alignment.CenterStart
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    if (!isNumeric || it.all { c -> c.isDigit() }) {
                        onValueChange(it)
                    }
                },
                singleLine = singleLine,
                maxLines = maxLines,
                textStyle = FutsalggTypography.regular_17_200.copy(
                    color = FutsalggColor.mono900
                ),
                cursorBrush = SolidColor(FutsalggColor.mint500),
                visualTransformation = visualTransformation,
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text,
                    imeAction = imeAction
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onImeAction() }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        onFocusChanged(focusState.isFocused)
                    },
                decorationBox = { innerTextField ->
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
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
