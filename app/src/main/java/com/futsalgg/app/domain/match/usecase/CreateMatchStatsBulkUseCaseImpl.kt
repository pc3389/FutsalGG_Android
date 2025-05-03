package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.CreateBulkMatchStat
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class CreateMatchStatsBulkUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : CreateMatchStatsBulkUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String,
        stats: List<CreateBulkMatchStat>
    ): Result<Unit> {
        return matchRepository.createMatchStatsBulk(
            accessToken = accessToken,
            matchId = matchId,
            stats = stats
        )
    }
} 