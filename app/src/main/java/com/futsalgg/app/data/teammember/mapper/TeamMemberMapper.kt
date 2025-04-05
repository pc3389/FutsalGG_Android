package com.futsalgg.app.data.teammember.mapper

import com.futsalgg.app.domain.teammember.model.TeamMember
import com.futsalgg.app.remote.api.teammember.model.response.TeamMemberResponse

fun TeamMemberResponse.toDomain(): TeamMember = TeamMember(
    id = id,
    name = name,
    createdTime = createdTime
) 