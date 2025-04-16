package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import javax.inject.Inject

class JoinTeamUseCaseImpl @Inject constructor(
    private val teamMemberRepository: TeamMemberRepository
) : JoinTeamUseCase {
    override suspend fun invoke(
        accessToken: String,
        teamId: String
    ): Result<Unit> {
        return teamMemberRepository.joinTeam(
            accessToken, teamId
        )
    }
}