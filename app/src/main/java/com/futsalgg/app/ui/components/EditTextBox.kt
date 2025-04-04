package com.futsalgg.app.ui.components

import androidx.annotation.StringRes
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun EditTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes hint: Int = R.string.signup_nickname_hint,
    onFocusChanged: (Boolean) -> Unit = {},
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    singleLine: Boolean = true,
    maxLines: Int = 1,
    isNumeric: Boolean = false,
    maxLength: Int? = null
) {
    //TODO MaxLength!!
    val focusRequester = remember { androidx.compose.ui.focus.FocusRequester() }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, FutsalggColor.mono500, RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp)
            .clickable { focusRequester.requestFocus() },
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = value,
            onValueChange = {
                if (!isNumeric || it.all { c -> c.isDigit() || c == '.' }) {
                    onValueChange(it)
                }
            },
            singleLine = singleLine,
            maxLines = maxLines,
            textStyle = FutsalggTypography.regular_17_200.copy(
                color = FutsalggColor.mono900
            ),
            cursorBrush = SolidColor(FutsalggColor.mint500),
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = { onImeAction() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    onFocusChanged(focusState.isFocused)
                },
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
    }
}