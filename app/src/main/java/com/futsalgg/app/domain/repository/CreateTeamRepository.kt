package com.futsalgg.app.domain.repository

import com.futsalgg.app.data.model.response.TeamLogoPresignedUrlResponse

interface CreateTeamRepository {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponse>
    
    suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponse>
    
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