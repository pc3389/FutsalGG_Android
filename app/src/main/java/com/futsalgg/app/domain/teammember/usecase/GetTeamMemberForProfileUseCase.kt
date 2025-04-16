package com.futsalgg.app.domain.teammember.usecase

import com.futsalgg.app.domain.teammember.model.TeamMemberProfile

interface GetTeamMemberForProfileUseCase {
    suspend operator fun invoke(
        accessToken: String,
        id: String?
    ): Result<TeamMemberProfile>
}