package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class RejectTeamMemberUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : RejectTeamMemberUseCase {
    override suspend fun invoke(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit> {
        return teamRepository.rejectTeamMember(
            accessToken = accessToken,
            teamMemberId = teamMemberId
        )
    }
} 