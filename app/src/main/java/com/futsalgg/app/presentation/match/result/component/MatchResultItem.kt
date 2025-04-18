package com.futsalgg.app.presentation.match.result.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.ui.components.SimpleIconAndText
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.components.state.IconState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun MatchResultItem(
    modifier: Modifier = Modifier,
    matchType: MatchType,
    opponentTeamName: String?,
    location: String,
    matchStartTime: String?,
    matchEndTime: String?,
    buttonEnabled: Boolean = true,
    onResultClick: () -> Unit
) {
    val borderColor = if (matchType == MatchType.INTER_TEAM) {
        FutsalggColor.blue100
    } else {
        FutsalggColor.mint100
    }
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = FutsalggColor.white
        ),
        border = BorderStroke(
            width = 1.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        if (matchType == MatchType.INTER_TEAM) {
                            FutsalggColor.blue50
                        } else {
                            FutsalggColor.mint50
                        }
                    )
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    text = matchType.toString(),
                    style = FutsalggTypography.bold_20_300
                )
                IconButton(
                    onClick = {
                        // TODO OnCLick
                    }
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_meatball_menu),
                        contentDescription = ""
                    )
                }
            }
            HorizontalDivider(
                thickness = 1.dp,
                color = borderColor
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                opponentTeamName?.let {
                    SimpleIconAndText(
                        modifier = Modifier.padding(vertical = 8.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_vs_20),
                        text = it
                    )
                }

                SimpleIconAndText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_location_20),
                    text = location
                )

                SimpleIconAndText(
                    modifier = Modifier.padding(vertical = 8.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_clock_20),
                    text = "${matchStartTime ?: "00:00"} ~ ${matchEndTime ?: "00:00"}"
                )

                VerticalSpacer8()

                if (buttonEnabled) {
                    SingleButton(
                        onClick = onResultClick,
                        modifier = Modifier.padding(vertical = 12.dp),
                        text = "경기 결과 확인하기",
                        containerColor = FutsalggColor.white,
                        contentColor = FutsalggColor.mono900,
                        hasBorder = true,
                        iconState = IconState()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MatchResultItemPreview() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        MatchResultItem(
            matchType = MatchType.INTER_TEAM,
            opponentTeamName = "FC 서울",
            location = "서울시 강남구 풋살장",
            matchStartTime = "21:00",
            matchEndTime = "22:00",
            buttonEnabled = false,
            onResultClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        MatchResultItem(
            matchType = MatchType.INTRA_SQUAD,
            opponentTeamName = null,
            location = "서울시 강남구 풋살장",
            matchStartTime = "21:00",
            matchEndTime = "22:00",
            onResultClick = {}
        )
    }
} 