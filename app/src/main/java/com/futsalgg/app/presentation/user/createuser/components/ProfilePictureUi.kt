package com.futsalgg.app.presentation.user.createuser.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.ui.components.ProfileImageWithCameraButton
import com.futsalgg.app.ui.components.TextWithInfoIcon

@Composable
fun ProfilePictureUi(
    titleText: String = stringResource(R.string.signup_profile_title),
    infoText: String = stringResource(R.string.signup_profile_info),
    image: Painter = painterResource(R.drawable.default_profile),
    croppedImage: Bitmap?,
    onSelectImageClick: () -> Unit,

    ) {
    TextWithInfoIcon(
        text = titleText,
        info = infoText
    )

    Spacer(Modifier.height(8.dp))

    ProfileImageWithCameraButton(
        image = croppedImage?.asImageBitmap()?.let { remember { BitmapPainter(it) } }
            ?: image,
        onCameraClick = onSelectImageClick
    )
}