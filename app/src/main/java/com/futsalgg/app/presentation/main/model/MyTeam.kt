package com.futsalgg.app.presentation.main.model

import com.futsalgg.app.presentation.common.model.TeamRole

data class MyTeam(
    val id: String = "",
    val teamMemberId: String = "",
    val name: String = "",
    val introduction: String = "",
    val rule: String = "",
    val logoUrl: String = "",
    val role: TeamRole = TeamRole.TEAM_MEMBER,
    val createdTime: String = "",
    val access: TeamRole = TeamRole.TEAM_LEADER,
)

