package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class CreateMatchStatUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : CreateMatchStatUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchParticipantId: String,
        roundNumber: Int,
        statType: MatchStat.StatType,
        assistedMatchStatId: String?
    ): Result<MatchStat> {
        return matchRepository.createMatchStat(
            accessToken = accessToken,
            matchParticipantId = matchParticipantId,
            roundNumber = roundNumber,
            statType = statType,
            assistedMatchStatId = assistedMatchStatId
        )
    }
} 