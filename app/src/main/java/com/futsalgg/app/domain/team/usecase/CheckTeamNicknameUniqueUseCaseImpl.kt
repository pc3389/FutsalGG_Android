package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class CheckTeamNicknameUniqueUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : CheckTeamNicknameUniqueUseCase {
    override suspend fun invoke(nickname: String): Result<Boolean> {
        return teamRepository.isTeamNicknameUnique(nickname)
    }
} 