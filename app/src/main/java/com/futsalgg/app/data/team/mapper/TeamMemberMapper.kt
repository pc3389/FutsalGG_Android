package com.futsalgg.app.data.team.mapper

import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.remote.api.team.model.response.TeamMemberResponse

fun TeamMemberResponse.toDomain(): TeamMember = TeamMember(
    id = id,
    name = name,
    createdTime = createdTime
) 