package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.futsalgg.app.R

@Composable
fun ProfileImageWithCameraButton(
    image: Painter = painterResource(R.drawable.default_profile),
    profileUrl: String? = null,
    imageSize: Dp = 120.dp,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Box {
            // 프로필 이미지 (120x120)
            AsyncImage(
                model = profileUrl,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(imageSize)
                    .align(Alignment.Center)
                    .clip(CircleShape),
                placeholder = image,
                error = image
            )

            // 카메라 버튼 정밀 위치 조정
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd) // 기준점: Profile의 BottomStart
                    .offset(
                        x = (20).dp, // 프로필 반지름에서 카메라 버튼 반지름을 빼서 Center 맞춤
                        y = 0.dp
                    )
            ) {
                IconButton(
                    onClick = onCameraClick,
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_album_40),
                        contentDescription = "Change Profile"
                    )
                }
            }
        }
    }
}
