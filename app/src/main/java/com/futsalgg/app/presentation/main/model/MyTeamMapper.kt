package com.futsalgg.app.presentation.main.model

import com.futsalgg.app.presentation.common.model.TeamRole
import com.futsalgg.app.domain.team.model.MyTeam as DomainMyTeam

object MyTeamMapper {
    fun toPresentation(domain: DomainMyTeam): MyTeam {
        return MyTeam(
            id = domain.id,
            teamMemberId = domain.teamMemberId,
            name = domain.name,
            introduction = domain.introduction,
            rule = domain.rule,
            logoUrl = domain.logoUrl,
            role = TeamRole.fromDomain(domain.role),
            createdTime = domain.createdTime,
            access = TeamRole.fromDomain(domain.access)
        )
    }
} 