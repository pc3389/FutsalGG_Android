package com.futsalgg.app.domain.teammember.usecase

import com.futsalgg.app.domain.teammember.model.TeamMemberProfile
import com.futsalgg.app.domain.teammember.repository.TeamMemberRepository
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
            teamMemberRepository.getTeamMember(accessToken, id)
        }
    }
}