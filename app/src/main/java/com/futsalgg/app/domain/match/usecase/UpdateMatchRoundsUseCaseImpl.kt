package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class UpdateMatchRoundsUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : UpdateMatchRoundsUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String,
        rounds: Int
    ): Result<Unit> {
        return matchRepository.updateMatchRounds(
            accessToken = accessToken,
            matchId = matchId,
            rounds = rounds
        )
    }
} 