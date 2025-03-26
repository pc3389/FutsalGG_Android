package com.futsalgg.app.domain.repository

import com.futsalgg.app.data.model.Platform
import com.futsalgg.app.data.model.response.LoginResponse

interface LoginRepository {
    suspend fun loginWithGoogleToken(
        token: String,
        platform: Platform = Platform.GOOGLE
    ): Result<LoginResponse>
}