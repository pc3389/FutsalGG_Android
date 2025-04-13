package com.futsalgg.app.presentation.setting

data class SettingState(
    val email: String = "",
    val name: String = "",
    val notification: Boolean = false,
    val profileUrl: String? = "",
)