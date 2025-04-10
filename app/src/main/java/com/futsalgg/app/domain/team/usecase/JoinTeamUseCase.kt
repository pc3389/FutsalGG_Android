package com.futsalgg.app.domain.team.usecase

interface JoinTeamUseCase {
    suspend operator fun invoke(teamId: String): Result<Unit>
}