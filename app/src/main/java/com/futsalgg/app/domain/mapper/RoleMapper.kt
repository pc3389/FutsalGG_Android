package com.futsalgg.app.domain.mapper

import com.futsalgg.app.remote.api.team.model.TeamRole as RemoteTeamRole
import com.futsalgg.app.domain.team.model.TeamRole


object RoleMapper {
    fun toDomain(role: RemoteTeamRole): TeamRole {
        return when (role) {
            RemoteTeamRole.OWNER -> TeamRole.OWNER
            RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
            RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
            RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
            RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
        }
    }
}