package com.futsalgg.app.presentation.main.model

data class MyTeam(
    val id: String,
    val teamMemberId: String,
    val name: String,
    val logoUrl: String,
    val role: TeamRole,
    val createdTime: String,
    val isManager: Boolean = true
)

enum class TeamRole {
    OWNER,
    TEAM_LEADER,
    TEAM_DEPUTY_LEADER,
    TEAM_SECRETARY,
    TEAM_MEMBER
} 