package com.futsalgg.app.presentation.common.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FutsalggColor.white)
            .pointerInput(Unit) {}, // 모든 터치 이벤트 차단
        contentAlignment = Alignment.Center
    ) {
        val context = LocalContext.current
        val imageLoader = ImageLoader.Builder(context)
            .components {
                add(GifDecoder.Factory())
            }
            .build()

        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(R.drawable.loading)
                .build(),
            imageLoader = imageLoader
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(200.dp) // 원하는 크기 설정
        )
    }
} 