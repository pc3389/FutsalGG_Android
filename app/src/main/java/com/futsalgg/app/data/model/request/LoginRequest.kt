package com.futsalgg.app.data.model.request

import com.futsalgg.app.data.model.Platform


data class LoginRequest(
    val token: String,
    val platform: Platform
)