package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class GetMatchesUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : GetMatchesUseCase {
    override suspend operator fun invoke(
        accessToken: String,
        page: Int,
        size: Int
    ): Result<List<Match>> = matchRepository.getMatches(
        accessToken = accessToken,
        page = page,
        size = size
    )
}