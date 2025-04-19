package com.futsalgg.app.presentation.match.updatesubteam

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.match.MatchSharedViewModel
import com.futsalgg.app.presentation.match.component.SelectableMathParticipantBox
import com.futsalgg.app.presentation.match.matchstat.model.MatchParticipantState
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer12
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun UpdateMatchParticipantsSubTeamScreen(
    navController: NavController,
    viewModel: UpdateMatchParticipantsSubTeamViewModel = hiltViewModel(),
    sharedViewModel: MatchSharedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val matchParticipantsState by sharedViewModel.matchParticipantsState.collectAsState()
    val matchState by sharedViewModel.matchState.collectAsState()

    // TODO SharedViewModel에서 자체전(내전) 가져오기
    BaseScreen(
        navController = navController,
        title = matchState.type.toString(),
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(FutsalggColor.white),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpacer32()
                Text(
                    text = stringResource(R.string.update_subteam_select_a_team_text),
                    style = FutsalggTypography.bold_24_400,
                    color = FutsalggColor.mono900
                )

                VerticalSpacer12()

                Text(
                    text = stringResource(R.string.update_subteam_sub_content_text),
                    style = FutsalggTypography.light_17_200,
                    color = FutsalggColor.mono900
                )

                VerticalSpacer16()
            }

            Column(
                modifier = Modifier.background(FutsalggColor.mono50)
            ) {
                HorizontalDivider(thickness = 1.dp, color = FutsalggColor.mono200)
                VerticalSpacer12()
            }

            LazyColumn(
                modifier = Modifier.weight(1f)
                    .background(FutsalggColor.mono50)
            ) {
                itemsIndexed(matchParticipantsState) { index, participant ->
                    SelectableMathParticipantBox(
                        onClick = {
                            viewModel.isSelected(participant.id)
                            sharedViewModel.updateParticipantSubteam(index)
                        },
                        participant = participant,
                        isSelected = participant.subTeam == MatchParticipantState.SubTeam.A
                    )
                }
            }

            BottomButton(
                text = stringResource(R.string.select_long),
                onClick = {
                    // TODO 마지막 온클릭
                },
            )
        }
    }
}