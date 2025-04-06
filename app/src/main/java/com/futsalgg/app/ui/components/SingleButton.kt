package com.futsalgg.app.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.state.IconState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

/**
 * FutsalGG 앱 커스텀 버튼.
 *
 * @param text text
 * @param onClick onClick
 * @param modifier modifier
 * @param containerColor container color
 * @param textStyle text style
 * @param hasBorder true시 회색 바탕을 보여준다.
 */
@Composable
fun SingleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = FutsalggColor.mono900,
    contentColor: Color = FutsalggColor.white,
    textStyle: TextStyle = FutsalggTypography.bold_17_200,
    enabled: Boolean = true,
    hasBorder: Boolean = false,
    iconState: IconState? = null,
) {
    var textWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = FutsalggColor.mono100,
            disabledContentColor = FutsalggColor.mono500
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (hasBorder) BorderStroke(1.dp, FutsalggColor.mono500) else null,
        contentPadding = PaddingValues(8.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = textStyle,
                onTextLayout = { textLayoutResult ->
                    textWidth = with(density) { textLayoutResult.size.width.toDp() }
                }

            )

            if (iconState != null) {
                val iconImage = ImageVector.vectorResource(iconState.iconRes)
                val offset = if (iconState.isIconLocationStart) {
                    -(textWidth/2 + iconState.iconPaddingFromText + 20.dp)
                } else {
                    (textWidth/2 + iconState.iconPaddingFromText)
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = offset)
                ) {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = "",
                        modifier = Modifier
                            .size(14.dp),
                        tint = contentColor
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewButton() {
    SingleButton(
        text = "경기aaajhasㅣㅁ라ㅓㅁ나ㅣㅇ럼",
        onClick = {},
        modifier = Modifier,
        iconState = IconState()
    )
}