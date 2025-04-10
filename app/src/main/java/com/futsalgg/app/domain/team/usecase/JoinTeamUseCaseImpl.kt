package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class JoinTeamUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : JoinTeamUseCase {
    override suspend fun invoke(teamId: String): Result<Unit> {
        return teamRepository.joinTeam(teamId)
    }
}