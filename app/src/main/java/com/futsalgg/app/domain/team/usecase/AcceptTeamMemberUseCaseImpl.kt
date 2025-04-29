package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class AcceptTeamMemberUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : AcceptTeamMemberUseCase {
    override suspend fun invoke(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit> {
        return teamRepository.acceptTeamMember(
            accessToken = accessToken,
            teamMemberId = teamMemberId
        )
    }
} 