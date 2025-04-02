package com.futsalgg.app.remote.api.auth.model.request

data class SignupRequest(
    val platform: String,
    val token: String,
    val nickname: String
) 