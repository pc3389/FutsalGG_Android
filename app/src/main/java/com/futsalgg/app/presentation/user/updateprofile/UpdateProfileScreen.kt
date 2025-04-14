package com.futsalgg.app.presentation.user.updateprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.imagecrop.rememberImagePickerLauncher
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.user.util.NicknameContents
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.EditTextBox
import com.futsalgg.app.ui.components.ProfileImageWithCameraButton
import com.futsalgg.app.ui.components.SimpleTitleText
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.spacers.VerticalSpacer32
import com.futsalgg.app.ui.components.spacers.VerticalSpacer8
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography

@Composable
fun UpdateProfileScreen(
    navController: NavController,
    viewModel: UpdateProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState().value
    val focusManager = LocalFocusManager.current
    val state = viewModel.profileState.collectAsState()
    val context = LocalContext.current

    val launchGalleryWithPermission = rememberImagePickerLauncher(
        navController = navController,
        viewModelType = RoutePath.UPDATE_PROFILE,
        context = context
    )

    BaseScreen(
        navController = navController,
        title = stringResource(R.string.update_profile_title),
        uiState = uiState
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .background(FutsalggColor.white)
                .fillMaxSize()
                .padding(innerPadding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
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

                    val croppedImage = state.value.croppedProfileImage

                    ProfileImageWithCameraButton(
                        modifier = Modifier.padding(top = 40.dp),
                        image = croppedImage?.asImageBitmap()
                            ?.let { remember { BitmapPainter(it) } }
                            ?: painterResource(R.drawable.default_profile),
                        onCameraClick = launchGalleryWithPermission,
                    )
                }

                VerticalSpacer32()

                TextWithStar(
                    text = stringResource(R.string.nickname_text)
                )

                VerticalSpacer8()

                NicknameContents(
                    nickname = state.value.nickname,
                    onNicknameChange = viewModel::updateName,
                    isCheckEnabled = state.value.isNicknameCheckEnabled,
                    nicknameState = state.value.nicknameState,
                    nicknameCheck = viewModel::checkNicknameDuplication,
                    context = context
                )

                Spacer(Modifier.height(26.dp))

                SimpleTitleText(
                    text = stringResource(R.string.squad_number_title)
                )

                VerticalSpacer8()

                EditTextBox(
                    value = state.value.squadNumber?.toString() ?: "",
                    onValueChange = viewModel::updateSquadNumber,
                    hint = stringResource(R.string.nickname_hint),
                    onImeAction = { focusManager.clearFocus() },
                    singleLine = true,
                    isNumeric = true,
                    hasDecimal = false
                )
            }
            BottomButton(
                modifier = Modifier.align(
                    Alignment.BottomCenter
                ),
                text = stringResource(R.string.squad_number_button_text),
                onClick = {
                    viewModel.updateProfile(
                        onSuccess = {
                            // TODO onSuccess 버튼
                        }
                    )
                },
                enabled = state.value.isFormValid,
                hasDivider = false
            )
        }
    }
}