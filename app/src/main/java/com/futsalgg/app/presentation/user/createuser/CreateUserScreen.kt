package com.futsalgg.app.presentation.user.createuser

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.imagecrop.rememberImagePickerLauncher
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.user.createuser.components.BirthdayUi
import com.futsalgg.app.presentation.user.createuser.components.GenderUi
import com.futsalgg.app.presentation.user.createuser.components.NicknameUi
import com.futsalgg.app.presentation.user.createuser.components.NotificationUi
import com.futsalgg.app.presentation.user.createuser.components.ProfilePictureUi
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.util.dateToRequestFormat
import com.futsalgg.app.util.toFile

@Composable
fun CreateUserScreen(
    navController: NavController,
    viewModel: CreateUserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val createUserState by viewModel.createUserState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()


    val launchGalleryWithPermission = rememberImagePickerLauncher(
        navController = navController,
        viewModelType = RoutePath.CREATE_USER,
        context = context
    )

    val scrollState = rememberScrollState()

    BaseScreen(
        navController = navController,
        screenName = RoutePath.CREATE_USER,
        title = stringResource(R.string.signup_toolbar_title),
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
                NicknameUi(
                    context = context,
                    nickname = createUserState.nickname,
                    nicknameState = createUserState.nicknameState,
                    onNicknameChange = viewModel::onNicknameChange,
                    nicknameCheck = { viewModel.checkNicknameDuplication() },
                    isCheckEnabled = createUserState.isNicknameCheckEnabled
                )

                Spacer(Modifier.height(26.dp))

                BirthdayUi(
                    context = context,
                    birthday = createUserState.birthday.dateToRequestFormat(),
                    onBirthdayChange = viewModel::onBirthdayChange,
                    birthdayState = createUserState.birthdayState,
                )

                Spacer(Modifier.height(26.dp))

                GenderUi(
                    gender = createUserState.gender,
                    onGenderButtonSelect = viewModel::onGenderChange
                )

                VerticalSpacer56()

                ProfilePictureUi(
                    onSelectImageClick = launchGalleryWithPermission,
                    croppedImage = createUserState.croppedProfileImage
                )

                VerticalSpacer56()

                NotificationUi(
                    isChecked = createUserState.notificationChecked,
                    onToggle = { viewModel.toggleNotification() }
                )

                VerticalSpacer56()
            }

            BottomButton(
                text = stringResource(R.string.signup_button),
                onClick = {
                    createUserState.croppedProfileImage?.let {
                        viewModel.uploadProfileImage(it.toFile(context))
                    }
                    viewModel.createUser(
                        onSuccess = {
                            viewModel.createUser {
                                navController.navigate(RoutePath.SELECT_TEAM)
                            }
                        }
                    )
                },
                enabled = createUserState.isFormValid
            )
        }
    }
}