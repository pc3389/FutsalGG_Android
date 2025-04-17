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

enum class TeamRole(val rank: Int, val displayName: String) {
    OWNER(0, "팀장"),
    TEAM_LEADER(1, "팀장"),
    TEAM_DEPUTY_LEADER(2, "부팀장"),
    TEAM_SECRETARY(3, "총무"),
    TEAM_MEMBER(4, "팀원")
}