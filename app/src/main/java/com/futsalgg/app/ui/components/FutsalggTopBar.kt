package com.futsalgg.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun FutsalggTopBar(
    title: String,
    onLeftIconClick: () -> Unit,
    rightIcon: ImageVector? = null,
    onRightClick: (() -> Unit)? = null,
    showMenu: Boolean = false
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(FutsalggColor.white),
        contentAlignment = Alignment.Center
    ) {
        // 중앙 타이틀
        Text(
            text = title,
            style = FutsalggTypography.bold_20_300,
            modifier = Modifier.align(Alignment.Center)
        )

        // 왼쪽 뒤로가기 아이콘
        IconButton(
            onClick = onLeftIconClick,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            val iconRes = if (showMenu) {
                R.drawable.ic_hamburger_24
            } else {
                R.drawable.ic_arrow_left_16
            }
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                contentDescription = "뒤로가기"
            )
        }

        // 오른쪽 아이콘 (옵셔널)
        rightIcon?.let {
            IconButton(
                onClick = { onRightClick?.invoke() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = rightIcon,
                    contentDescription = "오른쪽 아이콘"
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomStart),
            color = FutsalggColor.mono200
        )
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    FutsalggTopBar(
        title = "경기 일정 생성하기",
        onLeftIconClick = {},
        rightIcon = ImageVector.vectorResource(R.drawable.ic_arrow_forward_16),
        {}
    )
}