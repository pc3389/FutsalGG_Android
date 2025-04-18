package com.futsalgg.app.presentation.match.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.presentation.match.creatematchparticipants.MatchParticipantState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun SelectableMathParticipantBox(
    onClick: () -> Unit,
    participant: MatchParticipantState,
    isSelected: Boolean = participant.isSelected
) {
    val imageResource =
        if (isSelected) R.drawable.ic_checkbox_true_24 else R.drawable.ic_checkbox_false_24
    val borderColor =
        if (isSelected) FutsalggColor.mono900 else FutsalggColor.mono200
    Box(
        modifier = Modifier
            .padding(
                vertical = 4.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .border(
                    width = 1.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(16.dp))
            Image(
                modifier = Modifier
                    .padding(vertical = 12.dp),
                imageVector = ImageVector.vectorResource(imageResource),
                contentDescription = ""
            )
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f)
            ) {
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = participant.profileUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(32.dp),
                        placeholder = painterResource(R.drawable.default_profile),
                        error = painterResource(R.drawable.default_profile)
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        text = participant.name,
                        style = FutsalggTypography.bold_17_200,
                        color = FutsalggColor.mono900
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.width(44.dp),
                        text = participant.role,
                        style = FutsalggTypography.regular_17_200,
                        color = FutsalggColor.mono900,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}