package com.futsalgg.app.data.remote.api

import com.futsalgg.app.data.model.response.CheckNicknameResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {
    @GET("/users/check-nickname")
    suspend fun checkNickname(
        @Query("nickname") nickname: String
    ): Response<CheckNicknameResponse>
}