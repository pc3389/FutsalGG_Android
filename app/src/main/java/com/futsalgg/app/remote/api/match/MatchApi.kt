package com.futsalgg.app.remote.api.match

import com.futsalgg.app.remote.api.common.ApiListResponse
import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
import com.futsalgg.app.remote.api.match.model.response.MatchResponse
import com.futsalgg.app.remote.api.match.model.request.CreateMatchParticipantsRequest
import com.futsalgg.app.remote.api.match.model.response.CreateMatchParticipantsResponse
import com.futsalgg.app.remote.api.match.model.response.GetMatchResponse
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchParticipantsSubTeamRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchRoundsRequest
import com.futsalgg.app.remote.api.match.model.response.GetMatchStatsResponse
import com.futsalgg.app.remote.api.match.model.request.CreateMatchStatRequest
import com.futsalgg.app.remote.api.match.model.response.MatchStat
import com.futsalgg.app.remote.api.match.model.response.RecentMatchDateResponse
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchRequest
import com.futsalgg.app.remote.api.match.model.response.UpdateMatchResponse
import com.futsalgg.app.remote.api.match.model.response.MatchParticipantsResponse
import com.futsalgg.app.remote.api.match.model.request.CreateMatchStatsBulkRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchApi {
    @GET("matches")
    suspend fun getMatches(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20,
        @Query("teamId") teamId: String
    ): Response<ApiListResponse<MatchResponse>>

    @GET("matches/{id}")
    suspend fun getMatch(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<ApiResponse<GetMatchResponse>>

    @DELETE("matches/{id}")
    suspend fun deleteMatch(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<Unit>

    @POST("matches")
    suspend fun createMatch(
        @Header("Authorization") authHeader: String,
        @Body request: CreateMatchRequest
    ): Response<ApiResponse<MatchResponse>>

    @POST("match-participants")
    suspend fun createMatchParticipants(
        @Header("Authorization") accessToken: String,
        @Body request: CreateMatchParticipantsRequest
    ): Response<ApiResponse<CreateMatchParticipantsResponse>>

    @PATCH("match-participants/bulk/sub-team")
    suspend fun updateMatchParticipantsSubTeam(
        @Header("Authorization") accessToken: String,
        @Body request: UpdateMatchParticipantsSubTeamRequest
    ): Response<Unit>

    @PATCH("matches/{id}/rounds")
    suspend fun updateMatchRounds(
        @Header("Authorization") accessToken: String,
        @Path("id") matchId: String,
        @Body request: UpdateMatchRoundsRequest
    ): Response<Unit>

    @GET("match-participants")
    suspend fun getMatchParticipants(
        @Header("Authorization") accessToken: String,
        @Query("matchId") matchId: String
    ): Response<ApiResponse<MatchParticipantsResponse>>

    @GET("match-stats")
    suspend fun getMatchStats(
        @Header("Authorization") accessToken: String,
        @Query("matchId") matchId: String
    ): Response<ApiResponse<GetMatchStatsResponse>>

    @POST("match-stats")
    suspend fun createMatchStat(
        @Header("Authorization") accessToken: String,
        @Body request: CreateMatchStatRequest
    ): Response<ApiResponse<MatchStat>>

    @GET("matches/recent")
    suspend fun getRecentMatchDate(
        @Header("Authorization") accessToken: String,
        @Query("teamId") teamId: String
    ): Response<ApiResponse<RecentMatchDateResponse>>

    @PATCH("matches/{id}")
    suspend fun updateMatch(
        @Path("id") matchId: String,
        @Body request: UpdateMatchRequest,
        @Header("Authorization") accessToken: String
    ): Response<ApiResponse<UpdateMatchResponse>>

    @POST("match-stats/bulk")
    suspend fun createMatchStatsBulk(
        @Header("Authorization") accessToken: String,
        @Body request: CreateMatchStatsBulkRequest
    ): Response<Unit>
}