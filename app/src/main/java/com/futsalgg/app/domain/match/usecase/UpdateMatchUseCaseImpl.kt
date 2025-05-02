package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.UpdateMatch
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class UpdateMatchUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : UpdateMatchUseCase {
    override suspend fun invoke(
        accessToken: String,
        matchId: String,
        matchDate: String,
        location: String,
        startTime: String?,
        endTime: String?,
        substituteTeamMemberId: String?
    ): Result<UpdateMatch> {
        return matchRepository.updateMatch(
            accessToken = accessToken,
            matchId = matchId,
            matchDate = matchDate,
            location = location,
            startTime = startTime,
            endTime = endTime,
            substituteTeamMemberId = substituteTeamMemberId
        )
    }
} 