package com.futsalgg.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.presentation.team.teaminfo.TeamMemberState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun UserInfoRow(
    modifier: Modifier,
    profileUrl: String,
    name: String,
    role: TeamRole,
    status: TeamMemberState.TeamMemberStatus,
    endIcon: ImageVector? = null,
    onClick: () -> Unit
) {
    val newModifier = if (endIcon != null) {
        modifier.clickable {
            onClick()
        }
    } else modifier
    Box(
        modifier = Modifier
            .padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = newModifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = FutsalggColor.mono200,
                    shape = RoundedCornerShape(8.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(profileUrl)
                    .memoryCachePolicy(CachePolicy.ENABLED)  // 메모리 캐시 활성화
                    .build(),
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(start = 16.dp)
                    .size(32.dp)
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.default_profile),
                error = painterResource(R.drawable.default_profile)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                style = FutsalggTypography.bold_17_200,
                color = FutsalggColor.mono900,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.width(8.dp))
            Box (
                modifier = Modifier.padding()
            )
            when (status) {
                TeamMemberState.TeamMemberStatus.ACTIVE -> {
                    // Active User
                    Text(
                        modifier = Modifier.width(44.dp),
                        text = role.displayName,
                        style = FutsalggTypography.regular_17_200,
                        color = FutsalggColor.mono900,
                        textAlign = TextAlign.Center
                    )
                }
                TeamMemberState.TeamMemberStatus.INACTIVE -> {
                    // Inactive User
                    Box(
                        modifier = Modifier.width(44.dp)
                            .border(
                                width = 1.dp,
                                color = FutsalggColor.mono200,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = 6.dp),
                            text = stringResource(R.string.inactive_text),
                            style = FutsalggTypography.regular_15_100,
                            color = FutsalggColor.mono500
                        )
                    }
                }
                else -> {
                    // Pending User
                    Box(
                        modifier = Modifier.width(44.dp)
                            .background(
                                color = FutsalggColor.mint50,
                                shape = RoundedCornerShape(4.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = FutsalggColor.mint300,
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(vertical = 6.dp),
                            text = stringResource(R.string.apply_text),
                            style = FutsalggTypography.regular_15_100,
                            color = FutsalggColor.mint300
                        )
                    }
                }
            }
            Spacer(Modifier.width(40.dp))
        }

        endIcon?.let {
            Image(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterEnd),
                imageVector = it,
                contentDescription = ""
            )
        }
    }
}