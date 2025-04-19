package com.futsalgg.app.presentation.match.matchstat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.presentation.match.matchstat.model.MatchStat
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun ScoreBox(
    scoreInfo: List<MatchStat>,
    participants: List<MatchParticipantState>,
    backgroundColor: Color = FutsalggColor.mono800,
    borderColor: Color = FutsalggColor.mono500,
    emptyBorderColor: Color = FutsalggColor.mono800,
    isEditable: Boolean = false,
    onGoalClick: () -> Unit = {},
    onAssistClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val hasGoal = scoreInfo.isNotEmpty()
        val actualBorderColor = if (hasGoal || isEditable) borderColor else emptyBorderColor
        val actualBackgroundColor = if (hasGoal || isEditable) backgroundColor else Color.Transparent

        // 득점
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "득점",
                style = FutsalggTypography.bold_17_200,
                color = FutsalggColor.white
            )
            VerticalSpacer8()
            Box(
                modifier = Modifier
                    .height(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(actualBackgroundColor)
                    .border(
                        width = 1.dp,
                        color = actualBorderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                if (hasGoal) {
                    val profileUrl = participants.find {
                        it.id == scoreInfo[0].id
                    }?.profileUrl
                    AsyncImage(
                        model = profileUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        placeholder = painterResource(R.drawable.ic_team_default_56),
                        error = painterResource(R.drawable.ic_team_default_56)
                    )
                } else {
                    if (isEditable) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center)
                                .size(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add_14),
                            contentDescription = "",
                            tint = actualBorderColor
                        )
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "없음",
                            style = FutsalggTypography.bold_17_200,
                            color = actualBorderColor
                        )
                    }
                }
                if (isEditable && !hasGoal) {
                    Box(
                        Modifier.fillMaxSize()
                            .clickable {
                                onGoalClick()
                            }
                    )
                }
            }
        }


        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "도움",
                style = FutsalggTypography.bold_17_200,
                color = FutsalggColor.white
            )

            VerticalSpacer8()

            val hasAssist = scoreInfo.size == 2
            val actualAssistBorderColor = if (hasAssist || (hasGoal && isEditable)) borderColor else emptyBorderColor
            val actualAssistBackgroundColor = if (hasAssist || (hasGoal && isEditable)) backgroundColor else Color.Transparent

            Box(
                modifier = Modifier
                    .height(72.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(actualAssistBackgroundColor)
                    .border(
                        width = 1.dp,
                        color = actualAssistBorderColor,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                if (hasAssist) {
                    val profileUrl = participants.find {
                        scoreInfo.size == 2 && it.id == scoreInfo[1].id
                    }?.profileUrl
                    AsyncImage(
                        model = profileUrl,
                        contentDescription = "프로필 이미지",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        placeholder = painterResource(R.drawable.ic_team_default_56),
                        error = painterResource(R.drawable.ic_team_default_56)
                    )
                } else {
                    if (isEditable) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center)
                                .size(24.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_add_14),
                            contentDescription = "",
                            tint = actualAssistBorderColor
                        )
                    } else {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = "없음",
                            style = FutsalggTypography.bold_17_200,
                            color = actualBorderColor
                        )
                    }
                }
                if (isEditable && hasGoal && !hasAssist) {
                    Box(
                        Modifier.fillMaxSize()
                            .clickable {
                                onAssistClick()
                            }
                    )
                }
            }
        }
    }
}