package com.futsalgg.app.remote.api.team

import com.futsalgg.app.remote.api.team.model.request.JoinTeamRequest
import com.futsalgg.app.remote.api.team.model.response.GetTeamMemberResponse
import com.futsalgg.app.remote.api.team.model.response.GetTeamMembersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamMemberApi {
    @GET("team-members/active")
    suspend fun getTeamMembers(
        @Header("Authorization") authHeader: String,
        @Query("name") name: String,
        @Query("role") role: String = "TEAM-MEMBER"
    ): Response<GetTeamMembersResponse>

    @POST("team-members")
    suspend fun joinTeam(
        @Header("Authorization") accessToken: String,
        @Body request: JoinTeamRequest
    ): Response<Unit>

    @GET("team-members/me")
    suspend fun getMyTeamMember(
        @Header("Authorization") accessToken: String
    ): Response<GetTeamMemberResponse>

    @GET("team-members/{id}")
    suspend fun getTeamMemberWithId(
        @Header("Authorization") accessToken: String,
        @Path("id") id: String
    ): Response<GetTeamMemberResponse>
}