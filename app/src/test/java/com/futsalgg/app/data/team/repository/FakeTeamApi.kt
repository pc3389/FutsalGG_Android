package com.futsalgg.app.data.team.repository

import com.futsalgg.app.remote.api.team.TeamApi
import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.remote.api.team.model.response.CheckTeamNicknameResponse
import com.futsalgg.app.remote.api.team.model.response.TeamLogoPresignedUrlResponse
import retrofit2.Response

class FakeTeamApi : TeamApi {
    var shouldSucceed = true
    var isUnique = true

    override suspend fun checkTeamNickname(nickname: String): Response<CheckTeamNicknameResponse> {
        return if (shouldSucceed) {
            Response.success(CheckTeamNicknameResponse(isUnique))
        } else {
            Response.error(400, null)
        }
    }

    override suspend fun getTeamLogoPresignedUrl(authHeader: String): Response<TeamLogoPresignedUrlResponse> {
        return if (shouldSucceed) {
            Response.success(TeamLogoPresignedUrlResponse("presigned_url", "test_uri"))
        } else {
            Response.error(400, null)
        }
    }

    override suspend fun updateTeamLogo(
        authHeader: String,
        request: UpdateTeamLogoRequest
    ): Response<TeamLogoPresignedUrlResponse> {
        return if (shouldSucceed) {
            Response.success(TeamLogoPresignedUrlResponse("test_url", request.uri))
        } else {
            Response.error(400, null)
        }
    }

    override suspend fun createTeam(
        authHeader: String,
        request: CreateTeamRequest
    ): Response<Unit> {
        return if (shouldSucceed) {
            Response.success(Unit)
        } else {
            Response.error(400, null)
        }
    }
} 