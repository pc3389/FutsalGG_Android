package com.futsalgg.app.presentation.signup

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.futsalgg.app.R
import com.futsalgg.app.presentation.base.BaseScreen
import com.futsalgg.app.util.DateTransformation
import com.futsalgg.app.domain.model.EditTextState
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.ui.components.DoubleRadioButtonsEnum
import com.futsalgg.app.ui.components.EditTextWithState
import com.futsalgg.app.ui.components.SingleButton
import com.futsalgg.app.ui.components.TextWithStar
import com.futsalgg.app.ui.components.calendar.CalendarBottomSheet
import com.futsalgg.app.ui.theme.FutsalggColor
import com.futsalgg.app.ui.theme.FutsalggTypography
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SignupScreen(navController: NavController, viewModel: SignupViewModel = hiltViewModel()) {
    var nickname by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf(("")) }
    var showCalendarSheet by remember { mutableStateOf(false) }
    var gender by remember { mutableStateOf(Gender.MALE) }

    BaseScreen(
        navController = navController,
        title = R.string.signup_toolbar_title
    ) { innerPadding ->
        val context = LocalContext.current
        Column(
            Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            NicknameUi(
                context = context,
                nickName = nickname,
                onNicknameChange = { nickname = it }
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
        }
    }
}

@Composable
fun NicknameUi(
    context: Context,
    nickName: String,
    onNicknameChange: (String) -> Unit
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        EditTextWithState(
            modifier = Modifier
                .weight(3f)
                .padding(end = 8.dp),
            value = nickName, // ViewModel 연동 예정
            onValueChange = onNicknameChange,
            state = EditTextState.Default,
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
            containerColor = FutsalggColor.mono100,
            contentColor = FutsalggColor.mono500
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

fun String.toLocalDateOrNull(): LocalDate? {
    return try {
        LocalDate.parse(this)
    } catch (e: Exception) {
        null
    }
}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignupScreen(rememberNavController())
}