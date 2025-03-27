package com.futsalgg.app.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
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
 * @param hasIcon 기본적으로 표시하지 않으며, 아이콘을 따로 설정하지 않을 시 + 아이콘을 텍스트 옆에 표시
 * @param icon icon drawable res
 */
@Composable
fun SingleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = FutsalggColor.mono900,
    contentColor: Color = FutsalggColor.white,
    textStyle: TextStyle = FutsalggTypography.bold_17_200,
    hasBorder: Boolean = false,
    hasIcon: Boolean = false,
    @DrawableRes icon: Int = R.drawable.ic_add_14
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(8.dp),
        border = if (hasBorder) BorderStroke(1.dp, FutsalggColor.mono500) else null
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = textStyle
            )

            if (hasIcon) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(x = -((text.length * 4).dp + 16.dp))
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(icon),
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
        text = "Text",
        onClick = {},
        modifier = Modifier,
        hasIcon = true
    )
}