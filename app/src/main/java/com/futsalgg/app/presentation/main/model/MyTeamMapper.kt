package com.futsalgg.app.presentation.main.model

import com.futsalgg.app.domain.team.model.MyTeam as DomainMyTeam
import com.futsalgg.app.domain.team.model.TeamRole as DomainTeamRole

object MyTeamMapper {
    fun toPresentation(domain: DomainMyTeam): MyTeam {
        return MyTeam(
            id = domain.id,
            teamMemberId = domain.teamMemberId,
            name = domain.name,
            logoUrl = domain.logoUrl,
            role = when (domain.role) {
                DomainTeamRole.OWNER -> TeamRole.OWNER
                DomainTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                DomainTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                DomainTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                DomainTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
            },
            createdTime = domain.createdTime
        )
    }
} 