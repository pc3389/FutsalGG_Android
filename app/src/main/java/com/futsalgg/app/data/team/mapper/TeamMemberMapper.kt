package com.futsalgg.app.data.team.mapper

import com.futsalgg.app.data.common.mapper.GenderMapper.toDomain
import com.futsalgg.app.domain.mapper.RoleMapper
import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.domain.team.model.TeamMemberStatus
import com.futsalgg.app.remote.api.team.model.response.TeamMemberDetailResponse

fun TeamMemberDetailResponse.toDomain(teamId: String): TeamMember = TeamMember(
    id = id,
    name = name,
    role = RoleMapper.toDomain(role),
    profileUrl = profileUrl,
    birthDate = birthDate,
    generation = generation,
    gender = gender.toDomain(),
    status = when (status.name) {
        TeamMemberStatus.ACTIVE.name -> TeamMemberStatus.ACTIVE
        TeamMemberStatus.PENDING.name -> TeamMemberStatus.PENDING
        else -> TeamMemberStatus.INACTIVE
    },
    squadNumber = squadNumber,
    createdTime = createdTime,
    teamId = teamId
) 