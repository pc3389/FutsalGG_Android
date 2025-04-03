package com.futsalgg.app.presentation.user.signup

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
import androidx.compose.runtime.derivedStateOf
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
import com.futsalgg.app.presentation.common.screen.BaseScreen
import com.futsalgg.app.presentation.common.screen.LoadingScreen
import com.futsalgg.app.presentation.common.state.EditTextState
import com.futsalgg.app.presentation.common.state.UiState
import com.futsalgg.app.presentation.user.signup.components.BirthdayUi
import com.futsalgg.app.presentation.user.signup.components.GenderUi
import com.futsalgg.app.presentation.user.signup.components.NicknameUi
import com.futsalgg.app.presentation.user.signup.components.NotificationUi
import com.futsalgg.app.presentation.user.signup.components.ProfilePictureUi
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.util.toFile

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val nickname by viewModel.nickname.collectAsState()
    val isCheckEnabled by viewModel.isNicknameValid.collectAsState()
    val nicknameState by viewModel.nicknameState.collectAsState()

    val birthday by viewModel.birthday.collectAsState()
    val birthdayState by viewModel.birthdayState.collectAsState()
    val showCalendarSheet by viewModel.showCalendarSheet.collectAsState()

    val gender by viewModel.gender.collectAsState()

    val croppedImage by viewModel.croppedProfileImage.collectAsState()

    val notificationChecked by viewModel.notificationChecked.collectAsState()

    val signupButtonEnabled by remember {
        derivedStateOf {
            nicknameState == EditTextState.Available && birthdayState == EditTextState.Available
        }
    }

    val uiState by viewModel.uiState.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                // 이미지 선택되면 네비게이션
                navController.navigate("cropImage?uri=${Uri.encode(it.toString())}")
            }
        }
    )

    // 권한 요청 런처
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

    fun launchGalleryWithPermission() {
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

    BaseScreen(
        navController = navController,
        title = R.string.signup_toolbar_title
    ) { innerPadding ->
        Column(
            Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        // 키보드 닫기
                        focusManager.clearFocus()
                    })
                }
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .windowInsetsPadding(WindowInsets.navigationBars)
                .verticalScroll(rememberScrollState())
                .background(FutsalggColor.white),
            verticalArrangement = Arrangement.Top
        ) {
            NicknameUi(
                context = context,
                nickname = nickname,
                nicknameState = nicknameState,
                onNicknameChange = viewModel::onNicknameChange,
                nicknameCheck = { viewModel.checkNicknameDuplication() },
                isCheckEnabled = isCheckEnabled
            )

            VerticalSpacer56()

            BirthdayUi(
                context = context,
                birthday = birthday,
                onBirthdayChange = viewModel::onBirthdayChange,
                onCalendarClick = viewModel::onCalendarClick,
                showCalendarSheet = showCalendarSheet,
                onCalendarConfirm = {
                    viewModel.onBirthdaySelect(it)
                    viewModel.onDismissCalendar()
                },
                onDismissRequest = viewModel::onDismissCalendar,
                birthdayState = birthdayState,
                onValidateBirthday = viewModel::validateBirthday
            )

            VerticalSpacer56()

            GenderUi(
                gender = gender,
                onGenderButtonSelect = viewModel::onGenderChange
            )

            VerticalSpacer56()

            ProfilePictureUi(
                onSelectImageClick = { launchGalleryWithPermission() },
                croppedImage = croppedImage
            )

            VerticalSpacer56()

            NotificationUi(
                isChecked = notificationChecked,
                onToggle = { viewModel.toggleNotification() }
            )

            VerticalSpacer56()

            SingleButton(
                text = stringResource(R.string.signup_button),
                onClick = {
                    if (croppedImage != null) {
                        val file = croppedImage!!.toFile(context, "profile.jpg")
                        viewModel.uploadProfileImage(file)
                    }
                    viewModel.createUser(
                        onSuccess = {
                            // TODO 유저 등록 성공
                        }
                    )
                },
                enabled = signupButtonEnabled
            )
        }
    }

    if (uiState == UiState.Loading) {
        LoadingScreen()
    }
}

@Composable
fun VerticalSpacer56() {
    Spacer(modifier = Modifier.height(56.dp))
}