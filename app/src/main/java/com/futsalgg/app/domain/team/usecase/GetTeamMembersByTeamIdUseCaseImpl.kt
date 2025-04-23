package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamMembersByTeamIdUseCaseImpl @Inject constructor(
    private val teamMemberRepository: TeamMemberRepository
) : GetTeamMembersByTeamIdUseCase {
    override suspend operator fun invoke(accessToken: String, teamId: String): Result<List<TeamMember>> {
        return teamMemberRepository.getTeamMembersByTeamId(accessToken, teamId)
    }
} 