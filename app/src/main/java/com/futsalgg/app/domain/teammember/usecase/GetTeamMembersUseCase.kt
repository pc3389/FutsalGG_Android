package com.futsalgg.app.domain.teammember.usecase

import com.futsalgg.app.domain.teammember.model.TeamMember

interface GetTeamMembersUseCase {
    suspend operator fun invoke(
        accessToken: String,
        name: String,
        role: String = "TEAM-MEMBER"
    ): Result<List<TeamMember>>
} 