package com.futsalgg.app.remote.api.match

import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
import com.futsalgg.app.remote.api.match.model.response.MatchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MatchApi {
    @GET("matches")
    suspend fun getMatches(
        @Header("Authorization") authHeader: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Response<GetMatchesResponse>

    @DELETE("matches/{id}")
    suspend fun deleteMatch(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<Unit>

    @POST("matches")
    suspend fun createMatch(
        @Header("Authorization") authHeader: String,
        @Body request: CreateMatchRequest
    ): Response<MatchResponse>
}

data class GetMatchesResponse(
    val matches: List<MatchResponse>
) 