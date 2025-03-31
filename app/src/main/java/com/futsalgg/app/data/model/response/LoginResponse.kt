package com.futsalgg.app.data.model.response

data class  LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val isNew: Boolean
)