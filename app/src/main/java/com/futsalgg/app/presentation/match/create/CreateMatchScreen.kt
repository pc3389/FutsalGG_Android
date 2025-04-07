package com.futsalgg.app.presentation.match.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.match.model.MatchType
import com.futsalgg.app.ui.components.DateInputField
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.components.DoubleRadioButtonsEnum
import com.futsalgg.app.ui.components.FormRequiredAndHeader
import com.futsalgg.app.ui.components.TextWithStar

@Composable
fun CreateMatchScreen(
    navController: NavController,
    viewModel: CreateMatchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val createMatchState by viewModel.createMatchState.collectAsState()

    BaseScreen(
        navController = navController,
        title = stringResource(R.string.create_match_title),
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            FormRequiredAndHeader(
                headerText = stringResource(R.string.create_match_date),
            )
            DateInputField(
                value = createMatchState.matchDate,
                onValueChange = viewModel::onValidateMatchDate
            )

            TextWithStar(text = stringResource(R.string.signup_gender))
            Spacer(Modifier.height(8.dp))

            DoubleRadioButtonsEnum(
                selected = createMatchState.type,
                option1 = MatchType.INTRA_SQUAD,
                option2 = MatchType.INTER_TEAM,
                onSelect = viewModel::onTypeChange,
                label1 = MatchType.INTRA_SQUAD.toString(),
                label2 = MatchType.INTER_TEAM.toString()
            )

//            MatchTypeSelector(
//                selectedType = createMatchState.type,
//                onTypeSelected = viewModel::onTypeChange
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (createMatchState.type == MatchType.INTER_TEAM) {
//                OutlinedTextField(
//                    value = createMatchState.opponentTeamName,
//                    onValueChange = viewModel::onOpponentTeamNameChange,
//                    label = { Text("상대팀 이름") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//            }
//
//            OutlinedTextField(
//                value = createMatchState.location,
//                onValueChange = viewModel::onLocationChange,
//                label = { Text("장소") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            DateInputField(
//                value = createMatchState.matchDate,
//                onValueChange = viewModel::onMatchDateChange
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = createMatchState.startTime,
//                onValueChange = viewModel::onStartTimeChange,
//                label = { Text("시작 시간") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = createMatchState.endTime,
//                onValueChange = viewModel::onEndTimeChange,
//                label = { Text("종료 시간") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = createMatchState.description,
//                onValueChange = viewModel::onDescriptionChange,
//                label = { Text("설명") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(
//                onClick = { viewModel.createMatch {} },
//                modifier = Modifier.fillMaxWidth(),
//                enabled = createMatchState.isFormValid
//            ) {
//                Text("등록")
//            }
        }
    }
}