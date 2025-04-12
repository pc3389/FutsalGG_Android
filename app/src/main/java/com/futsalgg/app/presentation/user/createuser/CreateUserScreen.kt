package com.futsalgg.app.presentation.user.createuser

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.navigation.RoutePath
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.user.createuser.components.BirthdayUi
import com.futsalgg.app.presentation.user.createuser.components.GenderUi
import com.futsalgg.app.presentation.user.createuser.components.NicknameUi
import com.futsalgg.app.presentation.user.createuser.components.NotificationUi
import com.futsalgg.app.presentation.user.createuser.components.ProfilePictureUi
import com.futsalgg.app.ui.components.BottomButton
import com.futsalgg.app.ui.components.spacers.VerticalSpacer56
import com.futsalgg.app.ui.theme.FutsalggColor
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

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                navController.navigate(
                    "cropImage?uri=${Uri.encode(it.toString())}&viewModelType=${RoutePath.CREATE_USER}"
                )
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                imagePickerLauncher.launch("image/*")
            } else {
                Toast.makeText(context, "앨범 접근 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val launchGalleryWithPermission = remember {
        {
            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }

            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                imagePickerLauncher.launch("image/*")
            } else {
                permissionLauncher.launch(permission)
            }
        }
    }

    val scrollState = rememberScrollState()

    BaseScreen(
        navController = navController,
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
                    isCheckEnabled = createUserState.nickname.isNotEmpty()
                )

                Spacer(Modifier.height(26.dp))

                BirthdayUi(
                    context = context,
                    birthday = createUserState.birthday,
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
                    if (createUserState.croppedProfileImage != null) {
                        val file =
                            createUserState.croppedProfileImage!!.toFile(context, "profile.jpg")
                        viewModel.uploadProfileImage(file)
                    }
                    viewModel.createUser(
                        onSuccess = {
                            // TODO 유저 등록 성공 이후
                        }
                    )
                },
                enabled = createUserState.isFormValid
            )
        }
    }
}