package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import javax.inject.Inject

class CreateMatchParticipantsUseCaseImpl @Inject constructor(
    private val matchParticipantRepository: MatchParticipantRepository
) : CreateMatchParticipantsUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String,
        teamMemberIds: List<String>
    ): Result<List<MatchParticipant>> {
        return matchParticipantRepository.createMatchParticipants(
            accessToken = accessToken,
            matchId = matchId,
            teamMemberIds = teamMemberIds
        )
    }
} 