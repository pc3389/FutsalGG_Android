package com.futsalgg.app.domain.match.usecase

interface UpdateMatchRoundsUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String,
        rounds: Int
    ): Result<Unit>
} 