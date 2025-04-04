package com.futsalgg.app.presentation.team.createteam

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.team.model.MatchType
import com.futsalgg.app.ui.components.DropdownBox
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.TextWithInfoIcon
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun CreateTeamScreen(
    navController: NavController,
    viewModel: CreateTeamViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val scrollState = rememberScrollState()

    BaseScreen(
        navController = navController,
        title = R.string.create_team_title,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .verticalScroll(scrollState)
                .background(FutsalggColor.white),
            verticalArrangement = Arrangement.Top
        ) {
            // 팀명 입력
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.required),
                    style = FutsalggTypography.bold_17_200,
                    color = FutsalggColor.mint500,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopEnd)
                )
                TextWithStar(
                    text = stringResource(R.string.team_name),
                    modifier = Modifier.padding(top = 40.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            // 팀명 입력 필드와 중복확인 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                EditTextWithState(
                    modifier = Modifier
                        .weight(3f)
                        .padding(end = 8.dp),
                    value = "", // TODO: ViewModel에서 상태 관리
                    onValueChange = { /* TODO */ },
                    state = EditTextState.Initial,
                    messageProvider = { /* TODO */ "" },
                    imeAction = ImeAction.Done,
                    onImeAction = { /* TODO */ },
                    singleLine = true,
                    maxLines = 1,
                    maxLength = 10
                )

                SingleButton(
                    text = stringResource(R.string.check_duplication),
                    onClick = { /* TODO */ },
                    modifier = Modifier.weight(1f),
                    enabled = true // TODO: 상태에 따라 변경
                )
            }

            Spacer(Modifier.height(26.dp))


            // 팀 소개
            TextWithStar(
                text = stringResource(R.string.team_description),
            )
            Spacer(Modifier.height(8.dp))
            EditTextBox(
                value = "", // TODO: ViewModel에서 상태 관리
                onValueChange = { /* TODO */ },
                imeAction = ImeAction.Done,
                onImeAction = { /* TODO */ },
                singleLine = false,
                maxLines = 2,
                maxLength = 20,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpacer56()

            // 팀 규칙
            TextWithStar(
                text = stringResource(R.string.team_rules),
            )
            Spacer(Modifier.height(8.dp))

            EditTextBox(
                value = "", // TODO: ViewModel에서 상태 관리
                onValueChange = { /* TODO */ },
                imeAction = ImeAction.Done,
                onImeAction = { /* TODO */ },
                singleLine = false,
                maxLines = 2,
                modifier = Modifier.fillMaxWidth()
            )

            VerticalSpacer56()

            // 게임 유형
            TextWithStar(
                text = stringResource(R.string.team_game_type),
            )
            Spacer(Modifier.height(8.dp))
            DropdownBox(
                text = stringResource(R.string.select_please),
                items = MatchType.entries,
                onItemSelected = {},
            )

            VerticalSpacer56()

            // 관리자 범위
            TextWithStar(
                stringResource(R.string.team_admin_scope)
            )
            Spacer(Modifier.height(8.dp))
            DropdownBox(
                text = stringResource(R.string.select_please),
                items = Access.entries,
                onItemSelected = {},
            )

            VerticalSpacer56()

            // 회비
            TextWithInfoIcon(
                textWithStar = stringResource(R.string.team_fee),
                info = stringResource(R.string.info_number_keyboard),
            )
            Spacer(Modifier.height(8.dp))
            EditTextBox(
                value = "", // TODO: ViewModel에서 상태 관리
                onValueChange = { /* TODO */ },
                imeAction = ImeAction.Done,
                onImeAction = { /* TODO */ },
                singleLine = true,
                maxLines = 1,
                isNumeric = true,
                modifier = Modifier.fillMaxWidth()
            )
            VerticalSpacer56()

            // 로고
            TextWithInfoIcon(
                textWithStar = stringResource(R.string.team_logo),
                info = stringResource(R.string.info_number_keyboard),
            )
            Spacer(Modifier.height(8.dp))
            // TODO: 로고 업로드 구현

            VerticalSpacer56()

            // 생성하기 버튼
            SingleButton(
                text = stringResource(R.string.create_team),
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Composable
private fun VerticalSpacer56() {
    Spacer(modifier = Modifier.height(56.dp))
}