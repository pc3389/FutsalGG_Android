package com.futsalgg.app.presentation.user.createuser.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.futsalgg.app.R
import com.futsalgg.app.presentation.common.model.Gender
import com.futsalgg.app.ui.components.DoubleRadioButtonsEnum
import com.futsalgg.app.ui.components.TextWithStar

@Composable
fun GenderUi(
    gender: Gender,
    onGenderButtonSelect: (Gender) -> Unit
) {
    TextWithStar(text = stringResource(R.string.signup_gender))
    Spacer(Modifier.height(8.dp))

    DoubleRadioButtonsEnum(
        selected = gender,
        option1 = Gender.MAN,
        option2 = Gender.WOMAN,
        onSelect = onGenderButtonSelect,
        label1 = "남자",
        label2 = "여자"
    )
}