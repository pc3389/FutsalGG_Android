package com.futsalgg.app.presentation.common.imagecrop

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.futsalgg.app.util.toPx

@Composable
fun CropImage(
    imageUri: Uri,
    scale: Float,
    offset: Offset,
    rotation: Float,
    onScaleChange: (Float) -> Unit,
    onOffsetChange: (Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val painter = rememberAsyncImagePainter(imageUri)
    val minScale = 0.6f

    var gestureScale by remember { mutableFloatStateOf(1f) }
    var gestureOffset by remember { mutableStateOf(Offset.Zero) }

    val combinedScale = scale * gestureScale
    val combinedOffset = offset + gestureOffset

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    gestureScale = (gestureScale * zoom).coerceAtLeast(minScale)
                    gestureOffset += pan
                }
            }
    ) {
        val cropRadiusPx = 260.dp.toPx(context) / 2f
        val extraRangeRatio = 1.7f
        val imageSizeFactor = cropRadiusPx * combinedScale
        val maxOffset = imageSizeFactor * extraRangeRatio

        val constrainedOffset = Offset(
            x = combinedOffset.x.coerceIn(-maxOffset, maxOffset),
            y = combinedOffset.y.coerceIn(-maxOffset, maxOffset)
        )

        Image(
            painter = painter,
            contentDescription = "Crop Target",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .graphicsLayer {
                    scaleX = combinedScale
                    scaleY = combinedScale
                    translationX = constrainedOffset.x
                    translationY = constrainedOffset.y
                    rotationZ = rotation
                }
        )
    }

    // 제스처가 끝났을 때 상태 업데이트
    LaunchedEffect(gestureScale, gestureOffset) {
        onScaleChange(combinedScale)
        onOffsetChange(offset + gestureOffset)
        gestureScale = 1f
        gestureOffset = Offset.Zero
    }
}
