package com.futsalgg.app.remote.api.auth.model.response

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isNew: Boolean
)