package com.futsalgg.app.presentation.match.matchitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.model.MatchType
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.match.matchitem.create.component.TimeSelectorItem
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.DateInputField
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.FormRequiredAndHeader
import com.futsalgg.app.ui.components.SimpleTitleText
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import com.futsalgg.app.util.toTimeStringFormat

@Composable
fun BaseMatchScreen(
    navController: NavController,
    viewModel: BaseMatchViewModel,
    onBottomButtonClick: () -> Unit,
    titleText: String = stringResource(R.string.create_match_title),
    buttonText: String = stringResource(R.string.create_button_text)
    ) {
    val context = LocalContext.current
    val uiState by viewModel.uiStateFlow.collectAsState()
    val matchState by viewModel.matchStateFlow.collectAsState()
    val focusManager = LocalFocusManager.current

    BaseScreen(
        navController = navController,
        title = titleText,
        screenName = RoutePath.CREATE_MATCH,
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(FutsalggColor.white)
                .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                // 날짜
                FormRequiredAndHeader(
                    headerText = stringResource(R.string.create_match_date),
                )

                VerticalSpacer8()

                DateInputField(
                    value = matchState.match.matchDate,
                    onValueChange = viewModel::onValidateMatchDate,
                    state = matchState.matchDateState,
                    messageProvider = remember {
                        { state: DateState ->
                            when (state) {
                                DateState.ErrorStyle -> context.getString(R.string.date_error_wrong_date_format)
                                else -> null
                            }
                        }
                    },
                    canSelectFutureDate = true
                )

                Spacer(Modifier.height(26.dp))

                // TODO MVP 2 매치전
//                // 경기 유형
//                TextWithStar(text = stringResource(R.string.create_match_type))
//                VerticalSpacer8()
//
//                DoubleRadioButtonsEnum(
//                    selected = createMatchState.type,
//                    option1 = MatchType.INTRA_SQUAD,
//                    option2 = MatchType.INTER_TEAM,
//                    onSelect = viewModel::onTypeChange,
//                    label1 = MatchType.INTRA_SQUAD.toString(),
//                    label2 = MatchType.INTER_TEAM.toString()
//                )
//
//                VerticalSpacer56()

                // 장소
                TextWithStar(text = stringResource(R.string.create_match_location))
                VerticalSpacer8()
                EditTextBox(
                    value = matchState.match.location,
                    onValueChange = viewModel::onLocationChange,
                    hint = stringResource(R.string.edit_text_hint_within_15),
                    maxLength = 15
                )

                VerticalSpacer56()

                // 경기 시작 시간
                TextWithStar(text = stringResource(R.string.create_match_start_time_title))
                VerticalSpacer8()
                TimeSelectorItem(
                    knowTime = matchState.knowsStartTime,
                    time = matchState.match.startTime?.toTimeStringFormat() ?: "",
                    onSelected = viewModel::onKnowsStartTimeChange,
                    onTimeChange = viewModel::onStartTimeChange,
                    timeReady = viewModel::updateStartTimeReady
                )

                VerticalSpacer56()

                // 경기 종료 시간
                TextWithStar(text = stringResource(R.string.create_match_end_time_title))
                VerticalSpacer8()
                TimeSelectorItem(
                    knowTime = matchState.knowsEndTime,
                    time = matchState.match.endTime?.toTimeStringFormat() ?: "",
                    onSelected = viewModel::onKnowsEndTimeChange,
                    onTimeChange = viewModel::onEndTimeChange,
                    timeReady = viewModel::updateEndTimeReady
                )
                VerticalSpacer56()

                // 매치전일 경우에 상대팀 이름
                if (matchState.match.type == MatchType.INTER_TEAM) {
                    TextWithStar(text = stringResource(R.string.create_match_opponent_title))
                    VerticalSpacer8()
                    EditTextBox(
                        value = matchState.match.opponentTeamName ?: "",
                        onValueChange = viewModel::onOpponentTeamNameChange,
                        hint = stringResource(R.string.edit_text_hint_within_15),
                        maxLength = 15
                    )

                    VerticalSpacer56()
                }

                // 대리 기록자 선택
                SimpleTitleText(text = stringResource(R.string.create_match_sub_editor))
                VerticalSpacer8()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = FutsalggColor.mono500
                        )
                        .clickable {
                            // TODO 대리 기록자 선택하기
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(
                                vertical = 12.dp,
                                horizontal = 17.dp
                            ),
                        text = stringResource(R.string.enter_nickname_text),
                        style = FutsalggTypography.regular_17_200,
                        color = FutsalggColor.mono400
                    )

                    Image(
                        modifier = Modifier.padding(end = 16.dp),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_search_18),
                        contentDescription = ""
                    )
                }
                VerticalSpacer56()
            }

            BottomButton(
                text = buttonText,
                onClick = {
                   onBottomButtonClick()
                },
                enabled = matchState.isFormValid
            )
        }
    }
}