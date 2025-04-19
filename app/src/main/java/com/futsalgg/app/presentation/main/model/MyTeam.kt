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
    TEAM_MEMBER(4, "팀원");

    companion object {
        fun fromDomain(domainType: com.futsalgg.app.domain.team.model.TeamRole): TeamRole {
            return when (domainType) {
                com.futsalgg.app.domain.team.model.TeamRole.OWNER -> OWNER
                com.futsalgg.app.domain.team.model.TeamRole.TEAM_LEADER -> TEAM_LEADER
                com.futsalgg.app.domain.team.model.TeamRole.TEAM_DEPUTY_LEADER -> TEAM_DEPUTY_LEADER
                com.futsalgg.app.domain.team.model.TeamRole.TEAM_SECRETARY -> TEAM_SECRETARY
                com.futsalgg.app.domain.team.model.TeamRole.TEAM_MEMBER -> TEAM_MEMBER
            }
        }

        fun toDomain(presentationType: TeamRole): com.futsalgg.app.domain.team.model.TeamRole {
            return when (presentationType) {
                OWNER -> com.futsalgg.app.domain.team.model.TeamRole.OWNER
                TEAM_LEADER -> com.futsalgg.app.domain.team.model.TeamRole.TEAM_LEADER
                TEAM_DEPUTY_LEADER -> com.futsalgg.app.domain.team.model.TeamRole.TEAM_DEPUTY_LEADER
                TEAM_SECRETARY -> com.futsalgg.app.domain.team.model.TeamRole.TEAM_SECRETARY
                TEAM_MEMBER -> com.futsalgg.app.domain.team.model.TeamRole.TEAM_MEMBER
            }
        }
    }
}