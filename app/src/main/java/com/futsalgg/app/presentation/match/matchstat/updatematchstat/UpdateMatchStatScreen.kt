package com.futsalgg.app.presentation.match.matchstat.updatematchstat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.match.component.SelectableMathParticipantBox
import com.futsalgg.app.presentation.match.matchstat.base.BaseMatchStatScreen
import com.futsalgg.app.presentation.match.matchstat.component.ScoreTemplate
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.DoubleButtons
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateMatchStatScreen(
    navController: NavController,
    viewModel: UpdateMatchStatViewModel = hiltViewModel(),
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedRoundIndex by remember { mutableIntStateOf(0) }
    var selectedTeamIndex by remember { mutableIntStateOf(0) }
    var selectedGoalIndex by remember { mutableIntStateOf(0) }
    var selectedParticipantId by remember { mutableStateOf("") }
    var isGoal by remember { mutableStateOf(true) }

    val uiState by viewModel.uiStateFlow.collectAsState()
    val roundScoreState by viewModel.tempMatchStatsStateFlow.collectAsState()
    val participants by viewModel.participantsStateFlow.collectAsState()
    val matchState by viewModel.matchState.collectAsState()

    @Composable
    fun SelectGoalScorerBottomSheet(
        onScorerSelected: () -> Unit,
        onDismiss: () -> Unit = {}
    ) {
        val teamList = if (selectedTeamIndex == 0) {
            participants.filter {
                it.subTeam == MatchParticipantState.SubTeam.A
            }
        } else {
            participants.filter {
                it.subTeam == MatchParticipantState.SubTeam.B
            }
        }
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true
            ),
            containerColor = FutsalggColor.white,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            modifier = Modifier.fillMaxHeight(0.7f)
        ) {
            Column(
                modifier = Modifier.background(FutsalggColor.white)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "득점 선수를 선택해주세요.",
                    style = FutsalggTypography.bold_20_300,
                    color = FutsalggColor.mono900
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(teamList) { participant ->
                        SelectableMathParticipantBox(
                            participant = participant,
                            onClick = {
                                selectedParticipantId = if (selectedParticipantId == participant.id) {
                                    ""
                                } else {
                                    participant.id
                                }
                            },
                            isSelected = participant.id == selectedParticipantId,
                            iconTrue = R.drawable.ic_radio_true_24,
                            iconFalse = R.drawable.ic_radio_false_24
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
                BottomButton(
                    text = stringResource(R.string.select),
                    onClick = {
                        onScorerSelected()
                        onDismiss()
                    },
                    enabled = selectedParticipantId.isNotEmpty()
                )
            }
        }
    }

    BaseMatchStatScreen(
        navController = navController,
        screenName = RoutePath.UPDATE_MATCH_STAT,
        viewModel = viewModel,
        menuClick = {
            // TODO Menu Click
        },
        uiState = uiState
    ) {
        ScoreTemplate(
            roundStatsList = roundScoreState,
            participants = participants,
            isEditable = true,
            onGoalClick = { roundIndex, teamIndex ->
                selectedRoundIndex = roundIndex
                selectedTeamIndex = teamIndex
                isGoal = true
                showBottomSheet = true
            },
            onAssistClick = { roundIndex, teamIndex, goalIndex ->
                selectedRoundIndex = roundIndex
                selectedTeamIndex = teamIndex
                selectedGoalIndex = goalIndex
                isGoal = false
                showBottomSheet = true
            },
            onDeleteClick = { roundIndex, teamIndex, goalIndex ->
                viewModel.deleteGoal(roundIndex, teamIndex, goalIndex)
            }
        ) {
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
                        viewModel.createMatchStat()
                        navController.popBackStack(
                            route = RoutePath.MATCH_RESULT,
                            inclusive = false
                        )
                    },
                    isLeftBlack = false,
                    horizontalPadding = 16.dp,
                    verticalPadding = 16.dp
                )
            }
        }
    }

    if (showBottomSheet) {
        SelectGoalScorerBottomSheet(
            onScorerSelected = {
                if (isGoal) {
                    viewModel.addGoal(
                        roundIndex = selectedRoundIndex,
                        teamIndex = selectedTeamIndex,
                        matchParticipantId = selectedParticipantId,
                    )
                }
                if (!isGoal) {
                    viewModel.addAssist(
                        roundIndex = selectedRoundIndex,
                        teamIndex = selectedTeamIndex,
                        goalIndex = selectedGoalIndex,
                        matchParticipantId = selectedParticipantId
                    )
                }
                showBottomSheet = false
                selectedParticipantId = ""
            },
            onDismiss = {
                selectedParticipantId = ""
                showBottomSheet = false
            }
        )
    }
}