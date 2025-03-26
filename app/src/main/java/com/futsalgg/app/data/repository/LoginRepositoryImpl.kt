package com.futsalgg.app.data.repository

import com.futsalgg.app.data.model.Platform
import com.futsalgg.app.data.model.request.LoginRequest
import com.futsalgg.app.data.model.response.LoginResponse
import com.futsalgg.app.data.remote.api.LoginApi
import com.futsalgg.app.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository {
    override suspend fun loginWithGoogleToken(token: String, platform: Platform): Result<LoginResponse> {
        return try {
            val request = LoginRequest(token = token, platform = platform)
            val response = loginApi.login(request)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Server login failed: ${response.code()}"))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}