package com.futsalgg.app.presentation.match.matchitem.create.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically

@Composable
fun TimeSelectorItem(
    modifier: Modifier = Modifier,
    knowTime: Boolean = false,
    time: String,
    onSelected: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit
) {
    Column(modifier = modifier) {
        // 시간 선택 박스
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelected(true) }
                .border(
                    width = 1.dp,
                    color = if (knowTime) FutsalggColor.mono900 else FutsalggColor.mono200,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(
                            id = if (knowTime) R.drawable.ic_checkbox_true_24
                            else R.drawable.ic_checkbox_false_24
                        ),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = stringResource(R.string.create_match_time_known),
                        color = if (knowTime) FutsalggColor.mono900 else FutsalggColor.mono500,
                        style = FutsalggTypography.bold_17_200
                    )
                }

                AnimatedVisibility(
                    visible = knowTime,
                    enter = expandVertically(
                        animationSpec = tween(300)
                    ) + fadeIn(
                        animationSpec = tween(300)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(300)
                    ) + fadeOut(
                        animationSpec = tween(300)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                            .border(
                                width = 1.dp,
                                color = FutsalggColor.mono900,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .animateContentSize(
                                animationSpec = tween(300)
                            )
                    ) {
                        BasicTextField(
                            value = time,
                            onValueChange = onTimeChange,
                            textStyle = FutsalggTypography.regular_17_200,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            decorationBox = { innerTextField ->
                                if (time.isEmpty()) {
                                    Text(
                                        text = "00:00",
                                        color = FutsalggColor.mono400,
                                        style = FutsalggTypography.regular_17_200
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onSelected(false) }
                .border(
                    width = 1.dp,
                    color = if (knowTime) FutsalggColor.mono200 else FutsalggColor.mono900,
                    shape = RoundedCornerShape(8.dp)
                )
                .clip(RoundedCornerShape(8.dp))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(
                        id = if (knowTime) R.drawable.ic_checkbox_false_24
                        else R.drawable.ic_checkbox_true_24
                    ),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.create_match_time_known),
                    color = if (knowTime) FutsalggColor.mono500 else FutsalggColor.mono900,
                    style = FutsalggTypography.bold_17_200
                )
            }
        }
    }
}