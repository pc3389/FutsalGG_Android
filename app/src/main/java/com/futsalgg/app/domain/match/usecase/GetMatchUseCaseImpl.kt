package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class GetMatchUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : GetMatchUseCase {
    override suspend operator fun invoke(
        accessToken: String,
        id: String
    ): Result<Match> = matchRepository.getMatch(
        accessToken = accessToken,
        id = id
    )
} 