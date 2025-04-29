package com.futsalgg.app.remote.api.team

import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.remote.api.team.model.response.SearchTeamResponse
import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.remote.api.team.model.request.JoinTeamRequest
import com.futsalgg.app.remote.api.team.model.response.CheckTeamNicknameResponse
import com.futsalgg.app.remote.api.team.model.response.TeamLogoPresignedUrlResponse
import com.futsalgg.app.remote.api.team.model.response.GetMyTeamResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Path

interface TeamApi {
    @GET("teams/check-nickname")
    suspend fun checkTeamNickname(
        @Query("nickname") nickname: String
    ): Response<ApiResponse<CheckTeamNicknameResponse>>

    @GET("teams/{id}/logo-presigned-url")
    suspend fun getTeamLogoPresignedUrl(
        @Header("Authorization") authHeader: String,
    ): Response<ApiResponse<TeamLogoPresignedUrlResponse>>

    @PATCH("teams/{id}/logo")
    suspend fun updateTeamLogo(
        @Header("Authorization") authHeader: String,
        @Body request: UpdateTeamLogoRequest
    ): Response<ApiResponse<TeamLogoPresignedUrlResponse>>

    @POST("teams")
    suspend fun createTeam(
        @Header("Authorization") authHeader: String,
        @Body request: CreateTeamRequest
    ): Response<Unit>

    @GET("teams")
    suspend fun searchTeams(
        @Header("Authorization") accessToken: String,
        @Query("name") name: String
    ): Response<ApiResponse<SearchTeamResponse>>

    @GET("teams/me")
    suspend fun getMyTeam(
        @Header("Authorization") accessToken: String
    ): Response<ApiResponse<GetMyTeamResponse>>

    @PATCH("team-members/{id}/status/accepted")
    suspend fun acceptTeamMember(
        @Header("Authorization") accessToken: String,
        @Path("id") teamMemberId: String
    ): Response<ApiResponse<Unit>>
} 