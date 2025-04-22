package com.futsalgg.app.presentation.match.result.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.match.model.Match
import com.futsalgg.app.presentation.match.model.MatchStatus
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.match.model.VoteStatus
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.toDateFormat

@Composable
fun MatchResultPerDay(
    date: String, //yyyy-MM-dd
    matches: List<Match>,
    onResultClick: (Match) -> Unit,
    onScheduleEditClick: (Match) -> Unit,
    onResultEditClick: (Match) -> Unit,
    onDeleteClick: (Match) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            // 좌측 날짜 영역
            Column {
                Row(
                    modifier = Modifier.padding(top = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = FutsalggColor.mono900,
                                shape = CircleShape
                            )
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = date.toDateFormat(stringResource(R.string.date_format_dot_md)),
                        style = FutsalggTypography.bold_20_300
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    modifier = Modifier.padding(start = 20.dp),
                    text = "월요일",
                    style = FutsalggTypography.regular_17_200
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.width(26.dp))

            // 우측 매치 목록 영역
            Column {
                matches.forEach { match ->
                    MatchResultItem(
                        modifier = Modifier.padding(bottom = 16.dp),
                        matchType = match.type,
                        opponentTeamName = match.opponentTeamName,
                        location = match.location,
                        matchStartTime = match.startTime,
                        matchEndTime = match.endTime,
                        buttonEnabled = match.status == MatchStatus.COMPLETED,
                        onResultClick = { onResultClick(match) },
                        onScheduleEditClick = {
                            onScheduleEditClick(match)
                        },
                        onResultEditClick ={
                            onResultEditClick(match)
                        },
                        onDeleteClick ={
                            onDeleteClick(match)
                        }
                    )
                }
            }
        }
    }
}