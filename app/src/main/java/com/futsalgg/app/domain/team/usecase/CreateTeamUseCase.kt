package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import java.io.File

interface CreateTeamUseCase {
    suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean>

    suspend fun updateTeamLogo(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel>

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