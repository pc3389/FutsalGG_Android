package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.repository.MatchRepository
import javax.inject.Inject

class DeleteMatchUseCaseImpl @Inject constructor(
    private val matchRepository: MatchRepository
) : DeleteMatchUseCase {
    override suspend operator fun invoke(
        accessToken: String,
        id: String
    ): Result<Unit> = matchRepository.deleteMatch(
        accessToken = accessToken,
        id = id
    )
} 