package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match

interface GetMatchUseCase {
    suspend operator fun invoke(
        accessToken: String,
        id: String
    ): Result<Match>
} 