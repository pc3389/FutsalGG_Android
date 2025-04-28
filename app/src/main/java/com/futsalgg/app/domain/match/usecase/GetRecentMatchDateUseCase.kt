package com.futsalgg.app.domain.match.usecase

interface GetRecentMatchDateUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamId: String
    ): Result<String>
} 