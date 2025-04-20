package com.futsalgg.app.domain.auth.repository

import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.model.Platform

interface AuthRepository {
    suspend fun loginWithGoogleToken(
        platform: Platform = Platform.GOOGLE
    ): Result<LoginResponseModel>

    suspend fun signInWithGoogleIdToken(idToken: String): Result<Unit>

    suspend fun getFirebaseToken(): Result<String>
} 