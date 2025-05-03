package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.state.IconState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun EditTextBox(
    value: String,
    onValueChange: (String) -> Unit,
    textModifier: Modifier = Modifier.padding(vertical = 12.dp),
    boxModifier: Modifier = Modifier,
    hint: String = stringResource(R.string.nickname_hint),
    onFocusChanged: (Boolean) -> Unit = {},
    onImeDone: () -> Unit = {},
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    isNumeric: Boolean = false,
    hasDecimal: Boolean = true,
    iconState: IconState? = null,
    maxLength: Int? = null,
    showTailingText: Boolean = false,
    tailingText: String = ""
) {
    val focusRequester = remember { androidx.compose.ui.focus.FocusRequester() }
    Box(
        modifier = boxModifier
            .fillMaxWidth()
            .border(1.dp, FutsalggColor.mono500, RoundedCornerShape(8.dp))
            .clickable { focusRequester.requestFocus() },
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 16.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    if (!isNumeric || it.all { c -> c.isDigit() || (hasDecimal && c == '.') }) {
                        if (maxLength == null || it.length <= maxLength) {
                            onValueChange(it)
                        }
                    }
                },
                singleLine = singleLine,
                maxLines = maxLines,
                minLines = minLines,
                textStyle = FutsalggTypography.regular_17_200.copy(
                    color = FutsalggColor.mono900
                ),
                cursorBrush = SolidColor(FutsalggColor.mint500),
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onImeDone() }
                ),
                modifier = textModifier
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
            if (showTailingText) {
                Text(
                    text = tailingText,
                    style = FutsalggTypography.regular_17_200,
                    color = FutsalggColor.mono900
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (iconState != null) {
                IconButton(
                    onClick = iconState.onClick
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(iconState.iconRes),
                        contentDescription = ""
                    )
                }
            } else {
                Spacer(Modifier.width(16.dp))
            }
        }
    }
}