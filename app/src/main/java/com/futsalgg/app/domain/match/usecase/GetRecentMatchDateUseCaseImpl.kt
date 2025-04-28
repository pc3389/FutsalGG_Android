package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class GetRecentMatchDateUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : GetRecentMatchDateUseCase {

    override suspend fun invoke(
        accessToken: String,
        teamId: String
    ): Result<String> {
        return matchRepository.getRecentMatchDate(
            accessToken = accessToken,
            teamId = teamId
        )
    }
} 