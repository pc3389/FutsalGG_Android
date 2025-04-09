package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class CreateMatchUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : CreateMatchUseCase {
    override suspend operator fun invoke(
        accessToken: String,
        teamId: String,
        matchDate: String,
        type: MatchType,
        location: String,
        startTime: String?,
        endTime: String?,
        opponentTeamName: String?,
        substituteTeamMemberId: String?,
        description: String?,
        isVote: Boolean
    ): Result<Match> = matchRepository.createMatch(
        accessToken = accessToken,
        teamId = teamId,
        matchDate = matchDate,
        type = type,
        location = location,
        startTime = startTime,
        endTime = endTime,
        opponentTeamName = opponentTeamName,
        substituteTeamMemberId = substituteTeamMemberId,
        description = description,
        isVote = isVote
    )
} 