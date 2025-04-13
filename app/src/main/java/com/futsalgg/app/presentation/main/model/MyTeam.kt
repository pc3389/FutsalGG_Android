package com.futsalgg.app.presentation.main.model

data class MyTeam(
    val id: String,
    val teamMemberId: String,
    val name: String,
    val logoUrl: String,
    val role: TeamRole,
    val createdTime: String,
    val access: TeamRole,
){
    val isManager = (role.rank <= access.rank)
}

enum class TeamRole(val rank: Int) {
    OWNER(0),
    TEAM_LEADER(1),
    TEAM_DEPUTY_LEADER(2),
    TEAM_SECRETARY(3),
    TEAM_MEMBER(4)
} 