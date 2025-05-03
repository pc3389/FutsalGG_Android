package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import java.io.File
import javax.inject.Inject

class UpdateTeamLogoUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : UpdateTeamLogoUseCase {
    override suspend fun invoke(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel> {
        return teamRepository.uploadLogoImage(accessToken, file)
    }
} 