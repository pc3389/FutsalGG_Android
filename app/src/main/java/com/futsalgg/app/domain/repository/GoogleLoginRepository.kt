package com.futsalgg.app.domain.repository

interface GoogleLoginRepository {
    suspend fun signInWithGoogleIdToken(idToken: String): Result<Unit>
}