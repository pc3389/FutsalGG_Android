package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.SubTeam

interface UpdateMatchParticipantsSubTeamUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String,
        participantIds: List<String>,
        subTeam: SubTeam
    ): Result<Unit>
} 