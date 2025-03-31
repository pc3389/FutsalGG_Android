package com.futsalgg.app.presentation.signup

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.base.BaseScreen
import com.futsalgg.app.util.DateTransformation
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.ui.components.CheckableTextBox
import com.futsalgg.app.ui.components.DoubleRadioButtonsEnum
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.ProfileImageWithCameraButton
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.TextWithInfoIcon
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.calendar.CalendarBottomSheet
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val croppedImage = viewModel.croppedProfileImage

    var nickname by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf(("")) }
    var showCalendarSheet by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var notificationCheck by remember { mutableStateOf(false) }
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
                nickName = nickname,
                nicknameState = EditTextState.Available,
                onNicknameChange = {
                    nickname = it
                },
                isCheckEnabled = nickname.length >= 2
            )

            Spacer(Modifier.height(56.dp))

            BirthdayUi(
                context = context,
                birthday = birthday,
                onBirthdayChange = { birthday = it },
                showCalendarSheet = showCalendarSheet,
                onCalendarClick = { showCalendarSheet = true },
                onCalendarConfirm = { selectedDate ->
                    birthday = selectedDate.format(
                        DateTimeFormatter.ofPattern("yyyyMMdd")
                    ).toString()
                },
                onDismissRequest = { showCalendarSheet = false }
            )

            Spacer(Modifier.height(56.dp))

            GenderUi(
                gender = gender,
                onGenderButtonSelect = { gender = it }
            )

            Spacer(Modifier.height(56.dp))

            ProfilePictureUi(
                onSelectImageClick = { launchGalleryWithPermission() },
                croppedImage = croppedImage
            )

            Spacer(Modifier.height(56.dp))

            NotificationUi(
                notificationCheck = notificationCheck,
                onToggle = { notificationCheck = !notificationCheck }
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

@Composable
fun NicknameUi(
    context: Context,
    nickName: String,
    onNicknameChange: (String) -> Unit,
    nicknameState: EditTextState,
    isCheckEnabled: Boolean
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.signup_must),
            style = FutsalggTypography.bold_17_200,
            color = FutsalggColor.mint500,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.TopEnd)
        )
        TextWithStar(
            textRes = R.string.signup_nickname,
            modifier = Modifier.padding(top = 40.dp)
        )

    }

    Spacer(Modifier.height(8.dp))

    // 닉네임 입력 + 중복 확인 버튼
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        EditTextWithState(
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp),
            value = nickName, // ViewModel 연동 예정
            onValueChange = onNicknameChange,
            state = nicknameState,
            messageProvider = { st ->
                when (st) {
                    EditTextState.ErrorCannotUse -> context.getString(R.string.signup_nickname_error_message_cannot_use)
                    EditTextState.ErrorAlreadyExisting -> context.getString(R.string.signup_nickname_error_message_already)
                    EditTextState.Available -> context.getString(R.string.signup_nickname_available)
                    else -> null
                }
            }
        )

        SingleButton(
            text = "중복확인",
            onClick = { /* 중복 확인 로직 예정 */ },
            modifier = Modifier.weight(1f),
            enabled = isCheckEnabled
        )
    }
}

@Composable
fun BirthdayUi(
    context: Context,
    birthday: String,
    onBirthdayChange: (String) -> Unit,
    onCalendarClick: () -> Unit,
    showCalendarSheet: Boolean,
    onCalendarConfirm: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit

) {
    TextWithStar(
        textRes = R.string.signup_birthday,
    )

    Spacer(Modifier.height(8.dp))

    EditTextWithState(
        value = birthday,
        onValueChange = onBirthdayChange,
        modifier = Modifier.fillMaxWidth(),
        hint = R.string.signup_birthday_hint, // "YYYY-MM-DD"
        state = EditTextState.Default,
        messageProvider = { state ->
            when (state) {
                EditTextState.ErrorCannotUse -> context.getString(R.string.signup_birthday_error_invalid)
                else -> null
            }
        },
        trailingIcon = ImageVector.vectorResource(R.drawable.ic_calendar_20),
        showTrailingIcon = true,
        onTrailingIconClick = onCalendarClick,
        isNumeric = true,
        visualTransformation = DateTransformation()
    )

    if (showCalendarSheet) {
        CalendarBottomSheet(
            initialDate = birthday.toLocalDateOrNull() ?: LocalDate.now(),
            onConfirm = onCalendarConfirm,
            onDismissRequest = onDismissRequest
        )
    }
}

@Composable
fun GenderUi(
    gender: Gender,
    onGenderButtonSelect: (Gender) -> Unit
) {
    TextWithStar(
        textRes = R.string.signup_gender,
    )

    Spacer(Modifier.height(8.dp))

    DoubleRadioButtonsEnum(
        selected = gender,
        option1 = Gender.MALE,
        option2 = Gender.FEMALE,
        onSelect = onGenderButtonSelect,
        label1 = "남자",
        label2 = "여자"
    )
}

@Composable
fun ProfilePictureUi(
    onSelectImageClick: () -> Unit,
    croppedImage: Bitmap?
) {
    TextWithInfoIcon(
        text = stringResource(R.string.signup_profile_title),
        info = stringResource(R.string.signup_profile_info)
    )

    Spacer(Modifier.height(8.dp))

    ProfileImageWithCameraButton(
        image = croppedImage?.asImageBitmap()?.let { remember { BitmapPainter(it) } }
            ?: painterResource(R.drawable.default_profile),
        onCameraClick = { onSelectImageClick() }
    )
}

@Composable
fun NotificationUi(
    notificationCheck: Boolean,
    onToggle: () -> Unit
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "푸시 알림",
            style = FutsalggTypography.bold_20_300,
            color = FutsalggColor.mono900
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "(경기 일정, 투표 등록)",
            style = FutsalggTypography.regular_17_200,
            color = FutsalggColor.mono400
        )
    }

    Spacer(Modifier.height(8.dp))

    CheckableTextBox(
        text = "알림 받기",
        isChecked = notificationCheck,
        onToggle = onToggle
    )
}

fun String.toLocalDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}