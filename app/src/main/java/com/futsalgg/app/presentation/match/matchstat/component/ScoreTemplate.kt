package com.futsalgg.app.presentation.match.matchstat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.presentation.match.matchstat.model.RoundStats
import com.futsalgg.app.ui.components.DoubleButtons
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer20
import com.futsalgg.app.ui.components.spacers.VerticalSpacer24
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun ScoreTemplate(
    roundStatsList: List<RoundStats>,
    participants: List<MatchParticipantState>,
    isEditable: Boolean = false,
    onGoalClick: (Int, Int) -> Unit = { _, _ -> }, // roundIndex, team(0, 1)
    onAssistClick: (Int, Int, Int) -> Unit = { _, _, _ -> } // roudndIndex, team(0, 1), goalIndex
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val aWin = roundStatsList.sumOf { it.teamAStats.size }
            val bWin = roundStatsList.sumOf { it.teamBStats.size }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer20()
                    Text(
                        text = "A팀",
                        color = FutsalggColor.white,
                        style = FutsalggTypography.bold_20_300
                    )
                    VerticalSpacer16()
                    Text(
                        text = aWin.toString(),
                        color = FutsalggColor.white,
                        style = FutsalggTypography.bold_40_500
                    )
                    VerticalSpacer24()
                }
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    VerticalSpacer20()
                    Text(
                        text = "B팀",
                        color = FutsalggColor.mono900,
                        style = FutsalggTypography.bold_20_300
                    )
                    VerticalSpacer16()
                    Text(
                        text = bWin.toString(),
                        color = FutsalggColor.mono900,
                        style = FutsalggTypography.bold_40_500
                    )
                    VerticalSpacer24()
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            itemsIndexed(roundStatsList) { roundIndex, item ->
                Column {
                    // 판당 하얀 바
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = FutsalggColor.white.copy(alpha = 0.5f),
                            )
                    ) {
                        val aScore = item.teamAStats.size
                        val bScore = item.teamBStats.size
                        Row(
                            Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                        ) {
                            Text(
                                text = "${item.roundNumber}번째 판",
                                style = FutsalggTypography.bold_17_200,
                                color = FutsalggColor.mono900
                            )
                            Text(
                                text = " ($aScore : $bScore)",
                                style = FutsalggTypography.regular_17_200,
                                color = FutsalggColor.mono900
                            )
                        }
                    }

                    VerticalSpacer8()

                    Row(
                        modifier = Modifier.padding(
                            vertical = 4.dp
                        )
                    ) {
                        // Team A
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            item.teamAStats.forEachIndexed { index, scoreInfo ->
                                ScoreBox(
                                    scoreInfo = scoreInfo,
                                    participants = participants,
                                    isEditable = isEditable,
                                    onAssistClick = { onAssistClick(roundIndex, 0, index) }
                                )
                            }
                            if (!isEditable && item.teamAStats.isEmpty()) {
                                ScoreBox(
                                    scoreInfo = emptyList(),
                                    participants = participants
                                )
                            }
                            if (isEditable) {
                                ScoreBox(
                                    scoreInfo = emptyList(),
                                    participants = participants,
                                    isEditable = true,
                                    onGoalClick = { onGoalClick(roundIndex, 0) }
                                )
                            }
                        }

                        // Team B
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            item.teamBStats.forEachIndexed { index, scoreInfo ->
                                ScoreBox(
                                    scoreInfo = scoreInfo,
                                    participants = participants,
                                    backgroundColor = FutsalggColor.mint50,
                                    borderColor = FutsalggColor.mint500,
                                    emptyBorderColor = FutsalggColor.mint400,
                                    isEditable = isEditable,
                                    onAssistClick = { onAssistClick(roundIndex, 1, index) }
                                )
                            }
                            if (!isEditable && item.teamBStats.isEmpty()) {
                                ScoreBox(
                                    scoreInfo = emptyList(),
                                    participants = participants,
                                    backgroundColor = FutsalggColor.mint50,
                                    borderColor = FutsalggColor.mint500,
                                    emptyBorderColor = FutsalggColor.mint400,
                                )
                            }
                            if (isEditable) {
                                ScoreBox(
                                    scoreInfo = emptyList(),
                                    participants = participants,
                                    backgroundColor = FutsalggColor.mint50,
                                    borderColor = FutsalggColor.mint500,
                                    emptyBorderColor = FutsalggColor.mint400,
                                    isEditable = true,
                                    onGoalClick = { onGoalClick(roundIndex, 1) }
                                )
                            }
                        }
                    }
                    VerticalSpacer8()
                }
                VerticalSpacer16()
            }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = FutsalggColor.mono200
        )
        Box(
            modifier = Modifier.background(FutsalggColor.white)
        ) {
            DoubleButtons(
                leftText = "저장하기",
                rightText = "최종등록",
                onLeftClick = {
                    // TODO Left button click
                },
                onRightClick = {
                    // TODO Right button click
                },
                isLeftBlack = false,
                horizontalPadding = 16.dp,
                verticalPadding = 16.dp
            )
        }
    }
}