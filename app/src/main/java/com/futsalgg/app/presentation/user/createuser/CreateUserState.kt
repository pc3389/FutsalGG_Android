package com.futsalgg.app.presentation.user.createuser

import android.graphics.Bitmap
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.presentation.common.state.DateState
import com.futsalgg.app.presentation.common.state.EditTextState

data class CreateUserState(
    val nickname: String = "",
    val nicknameState: EditTextState = EditTextState.Initial,
    val birthday: String = "",
    val birthdayState: DateState = DateState.Initial,
    val gender: Gender = Gender.MALE,
    val croppedProfileImage: Bitmap? = null,
    val profileImageUrl: String? = null,
    val notificationChecked: Boolean = false,
    val errorMessage: String? = null
) {
    val isNicknameCheckEnabled: Boolean
        get() = nickname.length > 2

    val isFormValid: Boolean
        get() = nicknameState == EditTextState.Available && 
                birthdayState == DateState.Available
} 