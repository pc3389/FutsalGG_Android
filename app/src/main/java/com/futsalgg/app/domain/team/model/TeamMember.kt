package com.futsalgg.app.domain.team.model

import com.futsalgg.app.domain.team.model.TeamMemberStatus.ACTIVE
import com.futsalgg.app.domain.common.model.Gender

data class TeamMember(
    val id: String,
    val name: String,
    val role: TeamRole,
    val profileUrl: String?,
    val birthDate: String,
    val generation: String,
    val gender: Gender,
    val status: TeamMemberStatus = ACTIVE,
    val createdTime: String,
    val teamId: String
)

enum class TeamMemberStatus {
    ACTIVE,
    INACTIVE,
    PENDING
}