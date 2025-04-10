package com.futsalgg.app.domain.teammember.usecase

interface JoinTeamUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamId: String
    ): Result<Unit>
}