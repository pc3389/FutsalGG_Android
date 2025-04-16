package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMember

interface GetTeamMembersUseCase {
    suspend operator fun invoke(
        accessToken: String,
        name: String,
        role: String = "TEAM-MEMBER"
    ): Result<List<TeamMember>>
} 