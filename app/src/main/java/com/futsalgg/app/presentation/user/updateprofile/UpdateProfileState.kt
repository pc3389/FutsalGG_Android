package com.futsalgg.app.presentation.user.updateprofile

import android.graphics.Bitmap
import com.futsalgg.app.presentation.common.state.EditTextState

data class UpdateProfileState(
    val email: String,
    val nickname: String,
    val nicknameState: EditTextState = EditTextState.Initial,
    val squadNumber: Int?,
    val croppedProfileImage: Bitmap? = null,
    val profileUrl: String?,
    val notification: Boolean,
    val createdTime: String
) {
    val isNicknameCheckEnabled: Boolean
        get() = nickname.length > 2

    val isFormValid: Boolean
        get() = nicknameState == EditTextState.Available

    companion object {
        val Initial = UpdateProfileState(
            email = "",
            nickname = "",
            squadNumber = null,
            profileUrl = null,
            notification = false,
            createdTime = ""
        )
    }
} 