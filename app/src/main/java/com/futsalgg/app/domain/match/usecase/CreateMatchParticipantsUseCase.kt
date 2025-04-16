package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchParticipant

interface CreateMatchParticipantsUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String,
        teamMemberIds: List<String>
    ): Result<List<MatchParticipant>>
} 