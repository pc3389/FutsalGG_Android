package com.futsalgg.app.remote.api.team

import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.remote.api.team.model.response.CheckTeamNicknameResponse
import com.futsalgg.app.remote.api.team.model.response.TeamLogoPresignedUrlResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamApi {
    @GET("teams/check-nickname")
    suspend fun checkTeamNickname(
        @Query("nickname") nickname: String
    ): Response<CheckTeamNicknameResponse>

    @GET("teams/{id}/logo-presigned-url")
    suspend fun getTeamLogoPresignedUrl(
        @Header("Authorization") authHeader: String,
    ): Response<TeamLogoPresignedUrlResponse>

    @PATCH("teams/{id}/logo")
    suspend fun updateTeamLogo(
        @Header("Authorization") authHeader: String,
        @Body request: UpdateTeamLogoRequest
    ): Response<TeamLogoPresignedUrlResponse>

    @POST("teams")
    suspend fun createTeam(
        @Header("Authorization") authHeader: String,
        @Body request: CreateTeamRequest
    ): Response<Unit>
} 