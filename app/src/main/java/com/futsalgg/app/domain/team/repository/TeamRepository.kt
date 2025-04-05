package com.futsalgg.app.domain.team.repository

import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import java.io.File

interface TeamRepository {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun getTeamLogoPresignedUrl(accessToken: String): Result<TeamLogoPresignedUrlResponseModel>
    
    suspend fun updateTeamLogo(accessToken: String, uri: String): Result<TeamLogoResponseModel>

    suspend fun uploadLogoImage(accessToken: String, file: File): Result<TeamLogoResponseModel>
    
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