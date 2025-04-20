package com.futsalgg.app.presentation.splash

data class SplashState(
    val toLogin: Boolean = false,
    val toCreateUser: Boolean = false,
    val toSelectTeam: Boolean = false,
    val toMain: Boolean = false
)