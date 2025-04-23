package com.futsalgg.app.presentation.main.model

import com.futsalgg.app.presentation.common.mapper.RoleMapper
import com.futsalgg.app.domain.team.model.MyTeam as DomainMyTeam

object MyTeamMapper {
    fun toPresentation(domain: DomainMyTeam): MyTeam {
        return MyTeam(
            id = domain.id,
            teamMemberId = domain.teamMemberId,
            name = domain.name,
            logoUrl = domain.logoUrl,
            role = RoleMapper.toPresentation(domain.role),
            createdTime = domain.createdTime,
            access = RoleMapper.toPresentation(domain.access)
        )
    }
} 