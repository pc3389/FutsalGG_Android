package com.futsalgg.app.domain.team.repository

import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel

interface TeamRepository {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponseModel>
    
    suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponseModel>
    
    suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: String,
        access: String,
        dues: Int
    ): Result<Unit>
} 