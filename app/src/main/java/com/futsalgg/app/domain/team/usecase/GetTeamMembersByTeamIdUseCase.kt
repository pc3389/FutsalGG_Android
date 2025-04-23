package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.TeamMember
 
interface GetTeamMembersByTeamIdUseCase {
    suspend operator fun invoke(accessToken: String, teamId: String): Result<List<TeamMember>>
} 