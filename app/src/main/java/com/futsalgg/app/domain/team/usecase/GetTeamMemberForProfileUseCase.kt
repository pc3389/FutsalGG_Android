package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMemberProfile

interface GetTeamMemberForProfileUseCase {
    suspend operator fun invoke(
        accessToken: String,
        id: String?
    ): Result<TeamMemberProfile>
}