package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel

interface CreateTeamUseCase {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>

    suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponseModel>

    suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponseModel>

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