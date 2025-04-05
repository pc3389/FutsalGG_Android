package com.futsalgg.app.domain.teammember.repository

import com.futsalgg.app.domain.teammember.model.TeamMember

interface TeamMemberRepository {
    suspend fun getTeamMembers(
        accessToken: String,
        name: String,
        role: String = "TEAM-MEMBER"
    ): Result<List<TeamMember>>
} 