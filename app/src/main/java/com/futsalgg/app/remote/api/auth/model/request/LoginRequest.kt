package com.futsalgg.app.remote.api.auth.model.request

data class LoginRequest(
    val platform: String,
    val token: String
) 