package com.futsalgg.app.presentation.user.signup.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.ProfileImageWithCameraButton
import com.futsalgg.app.ui.components.TextWithInfoIcon

@Composable
fun ProfilePictureUi(
    croppedImage: Bitmap?,
    onSelectImageClick: () -> Unit
) {
    TextWithInfoIcon(
        text = "프로필 사진",
        info = "사진은 이후 수정이 가능합니다."
    )

    Spacer(Modifier.height(8.dp))

    ProfileImageWithCameraButton(
        image = croppedImage?.asImageBitmap()?.let { remember { BitmapPainter(it) } }
            ?: painterResource(R.drawable.default_profile),
        onCameraClick = onSelectImageClick
    )
}