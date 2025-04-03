package com.futsalgg.app.presentation.user.createuser

import android.graphics.Bitmap
import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.presentation.common.state.EditTextState

data class CreateUserState(
    val nickname: String = "",
    val nicknameState: EditTextState = EditTextState.Default,
    val birthday: String = "",
    val birthdayState: EditTextState = EditTextState.Default,
    val gender: Gender = Gender.MALE,
    val croppedProfileImage: Bitmap? = null,
    val profileImageUrl: String? = null,
    val notificationChecked: Boolean = false,
    val showCalendarSheet: Boolean = false,
    val errorMessage: String? = null
) {
    val isFormValid: Boolean
        get() = nicknameState == EditTextState.Available && 
                birthdayState == EditTextState.Available
} 