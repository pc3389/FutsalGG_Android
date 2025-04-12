package com.futsalgg.app.presentation.user.profile

data class ProfileUserState(
    val email: String = "",
    val name: String = "",
    val squadNumber: Int? = null,
    val notification: Boolean = false,
    val profileUrl: String? = "",
)