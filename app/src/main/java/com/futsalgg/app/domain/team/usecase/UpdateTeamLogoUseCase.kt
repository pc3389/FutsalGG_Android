package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import java.io.File

interface UpdateTeamLogoUseCase {
    suspend fun invoke(
        accessToken: String,
        teamId: String,
        file: File
    ): Result<TeamLogoResponseModel>
} 