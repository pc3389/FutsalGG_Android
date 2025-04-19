package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import javax.inject.Inject

class GetMatchParticipantsUseCaseImpl @Inject constructor(
    private val matchParticipantRepository: MatchParticipantRepository
) : GetMatchParticipantsUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String
    ): Result<List<MatchParticipant>> {
        return matchParticipantRepository.getMatchParticipants(
            accessToken = accessToken,
            matchId = matchId
        )
    }
} 