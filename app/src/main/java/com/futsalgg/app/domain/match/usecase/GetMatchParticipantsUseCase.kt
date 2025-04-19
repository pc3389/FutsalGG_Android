package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchParticipant

interface GetMatchParticipantsUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String
    ): Result<List<MatchParticipant>>
} 