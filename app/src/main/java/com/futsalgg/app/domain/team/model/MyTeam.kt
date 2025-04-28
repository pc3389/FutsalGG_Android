package com.futsalgg.app.domain.team.model

data class MyTeam(
    val id: String = "",
    val teamMemberId: String = "",
    val name: String = "",
    val introduction: String = "",
    val rule: String = "",
    val logoUrl: String = "",
    val role: TeamRole = TeamRole.TEAM_LEADER,
    val access: TeamRole = TeamRole.TEAM_LEADER,
    val createdTime: String
)

enum class TeamRole {
    OWNER,
    TEAM_LEADER,
    TEAM_DEPUTY_LEADER,
    TEAM_SECRETARY,
    TEAM_MEMBER
} 