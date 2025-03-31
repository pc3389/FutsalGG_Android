package com.futsalgg.app.presentation.signup

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.futsalgg.app.presentation.base.BaseScreen
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.presentation.signup.components.BirthdayUi
import com.futsalgg.app.presentation.signup.components.GenderUi
import com.futsalgg.app.presentation.signup.components.NicknameUi
import com.futsalgg.app.presentation.signup.components.NotificationUi
import com.futsalgg.app.presentation.signup.components.ProfilePictureUi
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.theme.FutsalggColor

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val nickname by viewModel.nickname.collectAsState()
    val isCheckEnabled by viewModel.isNicknameValid.collectAsState()

    val birthday by viewModel.birthday.collectAsState()
    val showCalendarSheet by viewModel.showCalendarSheet.collectAsState()

    val gender by viewModel.gender.collectAsState()

    val croppedImage by viewModel.croppedProfileImage.collectAsState()

    val notificationChecked by viewModel.notificationChecked.collectAsState()

    var signupButtonEnabled by remember { mutableStateOf(false) }

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
                nicknameState = EditTextState.Available,
                onNicknameChange = viewModel::onNicknameChange,
                isCheckEnabled = isCheckEnabled
            )

            Spacer(Modifier.height(56.dp))

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
                onDismissRequest = viewModel::onDismissCalendar
            )

            Spacer(Modifier.height(56.dp))

            GenderUi(
                gender = gender,
                onGenderButtonSelect = viewModel::onGenderChange
            )

            Spacer(Modifier.height(56.dp))

            ProfilePictureUi(
                onSelectImageClick = { launchGalleryWithPermission() },
                croppedImage = croppedImage
            )

            Spacer(Modifier.height(56.dp))

            NotificationUi(
                isChecked = notificationChecked,
                onToggle = { viewModel.toggleNotification() }
            )

            Spacer(Modifier.height(56.dp))

            SingleButton(
                text = stringResource(R.string.signup_button),
                onClick = {
                    //TODO Button on click
                },
                enabled = signupButtonEnabled
            )

            Spacer(Modifier.height(56.dp))
        }
    }
}