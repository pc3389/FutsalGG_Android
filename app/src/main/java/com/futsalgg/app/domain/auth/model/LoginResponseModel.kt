package com.futsalgg.app.domain.auth.model

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String,
    val isNew: Boolean
) 