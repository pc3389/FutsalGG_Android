package com.futsalgg.app.domain.team.repository

import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.domain.team.model.TeamMemberProfile

interface TeamMemberRepository {
    suspend fun getTeamMembers(
        accessToken: String,
        name: String,
        role: String = "TEAM-MEMBER"
    ): Result<List<TeamMember>>

    suspend fun getTeamMembersByTeamId(
        accessToken: String,
        teamId: String
    ): Result<List<TeamMember>>

    suspend fun joinTeam(
        accessToken: String,
        teamId: String
    ): Result<Unit>

    suspend fun getMyTeamMember(
        accessToken: String
    ): Result<TeamMemberProfile>

    suspend fun getTeamMemberWithId(
        accessToken: String,
        id: String
    ): Result<TeamMemberProfile>
} 