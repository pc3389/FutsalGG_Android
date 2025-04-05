package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.match.model.MatchType

interface CreateMatchUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamId: String,
        matchDate: String,
        type: MatchType,
        location: String,
        startTime: String? = null,
        endTime: String? = null,
        opponentTeamName: String? = null,
        substituteTeamMemberId: String? = null,
        description: String? = null,
        isVote: Boolean
    ): Result<Match>
} 