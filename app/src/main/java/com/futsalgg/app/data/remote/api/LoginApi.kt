package com.futsalgg.app.data.remote.api

import com.futsalgg.app.data.model.request.LoginRequest
import com.futsalgg.app.data.model.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}