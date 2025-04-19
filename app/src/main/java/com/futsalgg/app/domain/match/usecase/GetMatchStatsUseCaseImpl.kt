package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.RoundStats
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class GetMatchStatsUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : GetMatchStatsUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String
    ): Result<List<RoundStats>> {
        return matchRepository.getMatchStats(
            accessToken = accessToken,
            matchId = matchId
        )
    }
} 