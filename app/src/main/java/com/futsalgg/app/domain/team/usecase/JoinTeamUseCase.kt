package com.futsalgg.app.domain.team.usecase

interface JoinTeamUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamId: String
    ): Result<Unit>
}