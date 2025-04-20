package com.futsalgg.app.remote.api.auth

import com.futsalgg.app.remote.api.auth.model.request.LoginRequest
import com.futsalgg.app.remote.api.auth.model.response.LoginResponse
import com.futsalgg.app.remote.api.common.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<ApiResponse<LoginResponse>>
} 