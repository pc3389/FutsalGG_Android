package com.futsalgg.app.presentation.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MainScreenTopBar(
    title: String,
    onRightIconClick: () -> Unit,
    modifier: Modifier = Modifier
    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp)
            .background(FutsalggColor.white),
    ) {
        // 타이틀
        Text(
            text = title,
            style = FutsalggTypography.bold_20_300,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        // 오른쪽 아이콘 (옵셔널)
        IconButton(
            onClick = { onRightIconClick.invoke() },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_setting_32),
                contentDescription = "오른쪽 아이콘"
            )
        }
    }
}