package com.futsalgg.app.presentation.team.createorupdateteam

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.imagecrop.rememberImagePickerLauncher
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.screen.LoadingScreen
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.team.model.Access
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.DropdownBox
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.FormRequiredAndHeader
import com.futsalgg.app.ui.components.ProfileImageWithCameraButton
import com.futsalgg.app.ui.components.SimpleTitleText
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.TextWithInfoIcon
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.util.toFile

@Composable
fun BaseTeamModifyScreen(
    navController: NavController,
    viewModelType: String = RoutePath.CREATE_TEAM,
    viewModel: ModifyTeamViewModel,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    val modifyTeamState by viewModel.modifyTeamState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val launchGalleryWithPermission = rememberImagePickerLauncher(
        navController = navController,
        viewModelType = viewModelType,
        context = context
    )

    BaseScreen(
        navController = navController,
        screenName = RoutePath.CREATE_TEAM,
        title = stringResource(R.string.create_team),
        uiState = uiState
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .verticalScroll(scrollState)
                .background(FutsalggColor.white)
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    },
                verticalArrangement = Arrangement.Top
            ) {
                // 팀명 입력 필드와 중복확인 버튼
                FormRequiredAndHeader(
                    headerText = stringResource(R.string.team_name)
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    EditTextWithState(
                        modifier = Modifier
                            .weight(3f)
                            .padding(end = 8.dp),
                        value = modifyTeamState.teamName,
                        onValueChange = viewModel::onTeamNameChange,
                        hint = stringResource(R.string.team_name_hint),
                        state = modifyTeamState.teamNameState,
                        messageProvider = {
                            when (it) {
                                EditTextState.Available -> context.getString(R.string.create_team_duplication_available)
                                EditTextState.ErrorCannotUseSpecialChar -> context.getString(R.string.create_team_cannot_use_special_char)
                                EditTextState.ErrorAlreadyExisting -> context.getString(R.string.create_team_duplication_available)
                                EditTextState.ErrorCannotUseSlang -> context.getString(R.string.create_team_cannot_use_slang)
                                else -> null
                            }
                        },
                        imeAction = ImeAction.Done,
                        onImeAction = { focusManager.clearFocus() },
                        singleLine = true,
                        maxLines = 1,
                        maxLength = 10
                    )

                    SingleButton(
                        text = stringResource(R.string.check_duplication),
                        onClick = { viewModel.checkTeamNameDuplication() },
                        modifier = Modifier.weight(1f),
                        enabled = modifyTeamState.teamName.length > 2
                    )
                }

                Spacer(Modifier.height(26.dp))

                // 팀 소개
                SimpleTitleText(
                    text = stringResource(R.string.team_description),
                )
                Spacer(Modifier.height(8.dp))
                EditTextBox(
                    value = modifyTeamState.introduction,
                    onValueChange = viewModel::onIntroductionChange,
                    hint = stringResource(R.string.team_description_hint),
                    onImeDone = { focusManager.clearFocus() },
                    singleLine = false,
                    maxLines = 2,
                    minLines = 2,
                    maxLength = 20,
                    textModifier = Modifier.padding(vertical = 16.dp)
                )

                VerticalSpacer56()

                // 팀 규칙
                SimpleTitleText(
                    text = stringResource(R.string.team_rules),
                )
                Spacer(Modifier.height(8.dp))
                EditTextBox(
                    textModifier = Modifier.padding(vertical = 16.dp),
                    value = modifyTeamState.rule,
                    onValueChange = viewModel::onRuleChange,
                    hint = stringResource(R.string.team_rules_hint),
                    onImeDone = { focusManager.clearFocus() },
                    singleLine = false,
                    maxLines = 2,
                    minLines = 2,
                    maxLength = 30
                )

                VerticalSpacer56()

                // 관리자 범위
                TextWithStar(
                    stringResource(R.string.team_admin_scope)
                )
                Spacer(Modifier.height(8.dp))
                DropdownBox(
                    text = modifyTeamState.access?.toString()
                        ?: stringResource(R.string.select_please),
                    items = Access.entries,
                    onItemSelected = viewModel::onAccessChange,
                )

                VerticalSpacer56()

                // 게임 유형
                // TODO MVP 2에서 활성화
//                TextWithStar(
//                    text = stringResource(R.string.team_game_type),
//                )
//                Spacer(Modifier.height(8.dp))
//                DropdownBox(
//                    text = stringResource(R.string.select_please),
//                    items = MatchType.entries,
//                    onItemSelected = viewModel::onMatchTypeChange,
//                )
//
//                VerticalSpacer56()

                // 회비
                TextWithInfoIcon(
                    textWithStar = stringResource(R.string.team_fee),
                    info = stringResource(R.string.info_number_keyboard),
                )
                Spacer(Modifier.height(8.dp))

                EditTextBox(
                    value = modifyTeamState.dues,
                    onValueChange = viewModel::onDuesChange,
                    onImeDone = { focusManager.clearFocus() },
                    singleLine = true,
                    hint = stringResource(R.string.create_team_due_hint),
                    maxLines = 1,
                    isNumeric = true
                )

                VerticalSpacer56()

                // 로고
                TextWithInfoIcon(
                    textWithStar = stringResource(R.string.team_logo),
                    info = stringResource(R.string.info_number_keyboard),
                )

                Spacer(Modifier.height(8.dp))

                val croppedImage = modifyTeamState.croppedTeamImage

                ProfileImageWithCameraButton(
                    image = croppedImage?.asImageBitmap()?.let { remember { BitmapPainter(it) } }
                        ?: painterResource(R.drawable.img_team_default),
                    onCameraClick = launchGalleryWithPermission
                )

                VerticalSpacer56()
            }

            // 생성하기 버튼
            BottomButton(
                text = stringResource(R.string.create_team),
                onClick = {
                    modifyTeamState.croppedTeamImage?.let {
                        viewModel.uploadTeamImage(it.toFile(context))
                    }
                    onClick()
                },
                enabled = modifyTeamState.isFormValid
            )
        }
    }

    if (uiState is UiState.Loading) {
        LoadingScreen()
    }
}