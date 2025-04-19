package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.RoundStats

interface GetMatchStatsUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String
    ): Result<List<RoundStats>>
} 