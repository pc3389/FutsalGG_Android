package com.futsalgg.app.presentation.imagecrop

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.util.cropImageFromUri

@Composable
fun ProfileImageCropScreen(
    imageUri: Uri,
    onBack: () -> Unit,
    onConfirm: (Bitmap) -> Unit
) {
    val context = LocalContext.current

    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var rotation by remember { mutableFloatStateOf(0f) }

    // History stack for undo
    val undoStack = remember { mutableStateListOf<Pair<Offset, Float>>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FutsalggColor.mono900)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        // 이미지 조작 가능한 캔버스
        CropImage(
            imageUri = imageUri,
            scale = scale,
            offset = offset,
            rotation = rotation,
            onScaleChange = {
                undoStack.add(Pair(offset, rotation))
                scale = it
            },
            onOffsetChange = {
                undoStack.add(Pair(offset, rotation))
                offset = it
            },
            modifier = Modifier.fillMaxSize()
        )

        CropOverlay(visibleSize = 260.dp)

        // 상단 바 (뒤로, 선택)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_left_16),
                    tint = FutsalggColor.white,
                    contentDescription = "Back"
                )
            }

            Text(
                text = stringResource(R.string.select_long),
                color = FutsalggColor.mint500,
                modifier = Modifier.clickable {
                    cropImageFromUri(
                        context = context,
                        uri = imageUri,
                        scale = scale,
                        offset = offset,
                        rotation = rotation,
                        cropSize = 120.dp,
                        onCropped = onConfirm
                    )
                }
            )
        }

        // 하단 회전/undo 바
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_rotate_left_17),
                contentDescription = "Rotate Left",
                modifier = Modifier.clickable {
                    undoStack.add(Pair(offset, rotation))
                    rotation -= 90f
                }
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_undo_20),
                contentDescription = "Undo",
                modifier = Modifier.clickable {
                    if (undoStack.isNotEmpty()) {
                        val (prevOffset, prevRotation) = undoStack.removeAt(undoStack.lastIndex)
                        offset = prevOffset
                        rotation = prevRotation
                    }
                }
            )

            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_rotate_right_17),
                contentDescription = "Rotate Right",
                modifier = Modifier.clickable {
                    undoStack.add(Pair(offset, rotation))
                    rotation += 90f
                }
            )
        }
    }
}