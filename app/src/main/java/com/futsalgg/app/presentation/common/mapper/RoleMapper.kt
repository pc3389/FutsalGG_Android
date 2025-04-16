package com.futsalgg.app.presentation.common.mapper

import com.futsalgg.app.presentation.main.model.TeamRole
import com.futsalgg.app.domain.team.model.TeamRole as DomainTeamRole

object RoleMapper {
    fun roleMapper(role: DomainTeamRole): TeamRole {
        return when (role) {
            DomainTeamRole.OWNER -> TeamRole.OWNER
            DomainTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
            DomainTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
            DomainTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
            DomainTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
        }
    }
}