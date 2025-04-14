package com.futsalgg.app.domain.teammember.repository

import com.futsalgg.app.domain.teammember.model.TeamMember
import com.futsalgg.app.domain.teammember.model.TeamMemberProfile

interface TeamMemberRepository {
    suspend fun getTeamMembers(
        accessToken: String,
        name: String,
        role: String = "TEAM-MEMBER"
    ): Result<List<TeamMember>>

    suspend fun joinTeam(
        accessToken: String,
        teamId: String
    ): Result<Unit>

    suspend fun getMyTeamMember(
        accessToken: String
    ): Result<TeamMemberProfile>

    suspend fun getTeamMember(
        accessToken: String,
        id: String
    ): Result<TeamMemberProfile>
} 