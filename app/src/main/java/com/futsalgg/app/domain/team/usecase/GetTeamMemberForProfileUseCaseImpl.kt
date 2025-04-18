package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMemberProfile
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamMemberForProfileUseCaseImpl @Inject constructor(
    private val teamMemberRepository: TeamMemberRepository
) : GetTeamMemberForProfileUseCase {
    override suspend fun invoke(
        accessToken: String,
        id: String?
    ): Result<TeamMemberProfile> {
        return if (id == null) {
            teamMemberRepository.getMyTeamMember(accessToken)
        } else {
            teamMemberRepository.getTeamMemberWithId(accessToken, id)
        }
    }
}