package com.futsalgg.app.presentation.match.creatematchparticipants

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.spacers.VerticalSpacer16
import com.futsalgg.app.ui.components.spacers.VerticalSpacer4
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.components.state.IconState
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMatchParticipantsScreen(
    navController: NavController,
    viewModel: CreateMatchParticipantsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val matchParticipantsState by viewModel.matchParticipantsState.collectAsState()
    val matchState by viewModel.matchState.collectAsState()
    val showBottomSheet = remember { mutableStateOf(false) }

    BaseScreen(
        navController = navController,
        title = stringResource(R.string.create_match_participants_title),
        rightIcon = ImageVector.vectorResource(R.drawable.ic_search_18),
        onRightClick = {
            showBottomSheet.value = true
        },
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(FutsalggColor.mono50)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Column(
                        Modifier
                            .background(FutsalggColor.white)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        VerticalSpacer16()

                        // 날짜
                        Text(
                            text = matchState.matchDate,
                            style = FutsalggTypography.bold_20_300,
                            color = FutsalggColor.mono900
                        )
                        VerticalSpacer4()

                        // 시간
                        Text(
                            text = "${matchState.startTime} ~ ${matchState.endTime}",
                            style = FutsalggTypography.bold_20_300,
                            color = FutsalggColor.mono900
                        )
                        VerticalSpacer8()

                        // 장소
                        Text(
                            text = matchState.location,
                            style = FutsalggTypography.light_15_100,
                            color = FutsalggColor.mono900
                        )
                        VerticalSpacer16()
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = FutsalggColor.mono200
                        )

                        // 전체선택
                        Row(
                            modifier = Modifier
                                .align(Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(16.dp))
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        viewModel.toggleAllSelection()
                                    }
                            ) {
                                Image(
                                    modifier = Modifier.padding(16.dp),
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_checkbox_true_24),
                                    contentDescription = ""
                                )
                            }
                            Text(
                                text = stringResource(R.string.select_all),
                                color = FutsalggColor.mono900,
                                style = FutsalggTypography.bold_17_200
                            )
                        }
                    }
                }

                // 리스트
                item {
                    Spacer(Modifier.height(12.dp))
                }
                itemsIndexed(matchParticipantsState) { index, participant ->
                    val imageResource =
                        if (participant.isSelected) R.drawable.ic_checkbox_true_24 else R.drawable.ic_checkbox_false_24
                    val borderColor =
                        if (participant.isSelected) FutsalggColor.mono900 else FutsalggColor.mono200
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
                                    if (participant.isSelected) {
                                        viewModel.removeTeamMember(
                                            participant.teamMemberId
                                        )
                                        viewModel.updateIsSelected(index)
                                    } else {
                                        viewModel.addTeamMember(
                                            participant.teamMemberId
                                        )
                                        viewModel.updateIsSelected(index)
                                    }
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
                item {
                    Spacer(Modifier.height(12.dp))
                }
            }

            // 버튼
            BottomButton(
                text = stringResource(R.string.create_match_participants_button_text),
                onClick = viewModel::createMatchParticipants
            )
        }

        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = rememberModalBottomSheetState(
                    skipPartiallyExpanded = true
                ),
                containerColor = FutsalggColor.white,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                modifier = Modifier.fillMaxHeight(0.9f)
            ) {
                val focusManager = LocalFocusManager.current
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.create_match_participants_bottom_sheet_title),
                        style = FutsalggTypography.bold_20_300,
                        color = FutsalggColor.mono900
                    )
                    VerticalSpacer16()

                    // 검색바

                    EditTextBox(
                        value = "",
                        onValueChange = {},
                        boxModifier = Modifier
                            .padding(horizontal = 16.dp),
                        hint = stringResource(R.string.enter_nickname_text),
                        onImeDone = {
                            focusManager.clearFocus()
                            // TODO Search
                        },
                        iconState = IconState(
                            iconRes = R.drawable.ic_search_18,
                            onClick = {
                                focusManager.clearFocus()
                                // TODO Search
                            }
                        )
                    )
                    VerticalSpacer16()

                    // 팀원 목록
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        itemsIndexed(matchParticipantsState) { index, participant ->
                            val imageResource =
                                if (participant.isSelected) R.drawable.ic_checkbox_true_24 else R.drawable.ic_checkbox_false_24
                            val borderColor =
                                if (participant.isSelected) FutsalggColor.mono900 else FutsalggColor.mono200
                            Box(
                                modifier = Modifier
                                    .padding(
                                        vertical = 4.dp,
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
                                            if (participant.isSelected) {
                                                viewModel.removeTeamMember(
                                                    participant.teamMemberId
                                                )
                                                viewModel.updateIsSelected(index)
                                            } else {
                                                viewModel.addTeamMember(
                                                    participant.teamMemberId
                                                )
                                                viewModel.updateIsSelected(index)
                                            }
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
                    }

                    // 선택 버튼
                    BottomButton(
                        text = stringResource(R.string.select),
                        onClick = {
                            // TODO OnClick 선택
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}