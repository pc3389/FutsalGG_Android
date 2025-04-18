package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import javax.inject.Inject

class UpdateMatchParticipantsSubTeamUseCaseImpl @Inject constructor(
    private val matchParticipantRepository: MatchParticipantRepository
) : UpdateMatchParticipantsSubTeamUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String,
        participantIds: List<String>,
        subTeam: SubTeam
    ): Result<Unit> {
        return matchParticipantRepository.updateMatchParticipantsSubTeam(
            accessToken = accessToken,
            matchId = matchId,
            participantIds = participantIds,
            subTeam = subTeam
        )
    }
} 