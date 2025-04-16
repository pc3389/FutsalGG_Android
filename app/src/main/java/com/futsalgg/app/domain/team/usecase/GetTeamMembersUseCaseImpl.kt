package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamMembersUseCaseImpl @Inject constructor(
    private val teamMemberRepository: TeamMemberRepository
) : GetTeamMembersUseCase {
    override suspend fun invoke(
        accessToken: String,
        name: String,
        role: String
    ): Result<List<TeamMember>> = teamMemberRepository.getTeamMembers(
        accessToken = accessToken,
        name = name,
        role = role
    )
} 