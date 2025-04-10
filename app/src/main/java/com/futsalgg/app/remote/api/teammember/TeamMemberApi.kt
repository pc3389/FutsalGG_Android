package com.futsalgg.app.remote.api.teammember

import com.futsalgg.app.remote.api.team.model.request.JoinTeamRequest
import com.futsalgg.app.remote.api.teammember.model.response.GetTeamMembersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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
}