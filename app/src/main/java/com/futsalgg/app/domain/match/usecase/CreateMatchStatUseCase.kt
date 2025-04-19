package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchStat

interface CreateMatchStatUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchParticipantId: String,
        roundNumber: Int,
        statType: MatchStat.StatType,
        assistedMatchStatId: String?
    ): Result<MatchStat>
} 