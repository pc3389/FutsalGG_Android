package com.futsalgg.app.presentation.team.model

enum class Access {
    TEAM_LEADER,
    TEAM_DEPUTY_LEADER,
    TEAM_SECRETARY;

    override fun toString(): String {
        return when (this) {
            TEAM_LEADER -> "팀장만"
            TEAM_DEPUTY_LEADER -> "팀장 + 부팀장"
            TEAM_SECRETARY -> "팀장 + 부팀장 + 총무"
        }
    }

    companion object {
        fun fromDomain(domainAccess: com.futsalgg.app.domain.team.model.Access): Access {
            return when (domainAccess) {
                com.futsalgg.app.domain.team.model.Access.TEAM_LEADER -> TEAM_LEADER
                com.futsalgg.app.domain.team.model.Access.TEAM_DEPUTY_LEADER -> TEAM_DEPUTY_LEADER
                com.futsalgg.app.domain.team.model.Access. TEAM_SECRETARY -> TEAM_SECRETARY
                else -> TEAM_LEADER
            }
        }

        fun toDomain(presentationAccess: Access): com.futsalgg.app.domain.team.model.Access {
            return when (presentationAccess) {
                TEAM_LEADER -> com.futsalgg.app.domain.team.model.Access.TEAM_LEADER
                TEAM_DEPUTY_LEADER -> com.futsalgg.app.domain.team.model.Access.TEAM_DEPUTY_LEADER
                TEAM_SECRETARY -> com.futsalgg.app.domain.team.model.Access.TEAM_SECRETARY
            }
        }
    }
} 