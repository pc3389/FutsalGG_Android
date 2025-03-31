package com.futsalgg.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun LoadingScreen() {
    // 반투명 배경에 중앙에 CircularProgressIndicator 표시
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FutsalggColor.mono500.copy(alpha = 0.5f)), // 필요에 따라 색상과 투명도 조정
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
    }
}