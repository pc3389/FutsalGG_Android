package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match

interface GetMatchesUseCase {
    suspend operator fun invoke(
        accessToken: String,
        page: Int = 1,
        size: Int = 20,
        teamId: String
    ): Result<List<Match>>
} 