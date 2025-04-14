package com.futsalgg.app.domain.teammember.usecase

import com.futsalgg.app.domain.teammember.model.TeamMemberProfile
import com.futsalgg.app.domain.teammember.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamMemberUseCaseImpl @Inject constructor(
    private val teamMemberRepository: TeamMemberRepository
) : GetTeamMemberUseCase {

    override suspend operator fun invoke(
        accessToken: String,
        id: String
    ): Result<TeamMemberProfile> {
        return teamMemberRepository.getTeamMember(accessToken, id)
    }
} 