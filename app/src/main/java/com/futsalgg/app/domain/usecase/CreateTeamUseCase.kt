package com.futsalgg.app.domain.usecase

import com.futsalgg.app.data.model.response.TeamLogoPresignedUrlResponse
import com.futsalgg.app.domain.model.team.Access
import com.futsalgg.app.domain.model.team.MatchType

interface CreateTeamUseCase {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>

    suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponse>

    suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponse>

    suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit>
} 