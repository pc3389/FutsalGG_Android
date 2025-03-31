package com.futsalgg.app.presentation.imagecrop

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
                scale = it
            },
            onOffsetChange = {
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
                        cropSize = 260.dp,
                        onCropped = onConfirm
                    )
                }
            )
        }

        Column(
            Modifier
                .align(Alignment.BottomCenter)
                .height(52.dp)
                .fillMaxWidth()
        ) {
            HorizontalDivider(
                thickness = 1.dp,
                color = FutsalggColor.mono700
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_rotate_left_17),
                    contentDescription = "Rotate Left",
                    modifier = Modifier
                        .padding(18.dp)
                        .clickable {
                            rotation -= 90f
                        }
                )

                Spacer(Modifier.width(32.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_undo_20),
                    contentDescription = "Undo",
                    modifier = Modifier
                        .padding(18.dp)
                        .clickable {
                            scale = 1f
                            offset = Offset.Zero
                            rotation = 0f
                        }
                )

                Spacer(Modifier.width(32.dp))

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_rotate_right_17),
                    contentDescription = "Rotate Right",
                    modifier = Modifier
                        .padding(18.dp)
                        .clickable {
                            rotation += 90f
                        }
                )
            }
        }
    }
}