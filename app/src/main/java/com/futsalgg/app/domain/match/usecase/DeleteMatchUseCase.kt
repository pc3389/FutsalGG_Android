package com.futsalgg.app.domain.match.usecase

interface DeleteMatchUseCase {
    suspend operator fun invoke(
        accessToken: String,
        id: String
    ): Result<Unit>
}