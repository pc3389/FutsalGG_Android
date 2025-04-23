package com.futsalgg.app.presentation.match.matchstat.model

import com.futsalgg.app.presentation.common.model.TeamRole

data class MatchParticipantState(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: TeamRole,
    val profileUrl: String,
    val subTeam: SubTeam,
    val isSelected: Boolean = false,
    val createdTime: String
) {
    enum class SubTeam {
        NONE,
        A,
        B;

        companion object {
            fun fromDomain(domainType: com.futsalgg.app.domain.match.model.SubTeam): SubTeam {
                return when (domainType) {
                    com.futsalgg.app.domain.match.model.SubTeam.NONE -> NONE
                    com.futsalgg.app.domain.match.model.SubTeam.A -> A
                    com.futsalgg.app.domain.match.model.SubTeam.B -> B
                }
            }

            fun toDomain(presentationType: SubTeam): com.futsalgg.app.domain.match.model.SubTeam {
                return when (presentationType) {
                    NONE -> com.futsalgg.app.domain.match.model.SubTeam.NONE
                    A -> com.futsalgg.app.domain.match.model.SubTeam.A
                    B -> com.futsalgg.app.domain.match.model.SubTeam.B
                }
            }
        }
    }

    companion object {
        fun fromDomain(domain: com.futsalgg.app.domain.match.model.MatchParticipant): MatchParticipantState {
            return MatchParticipantState(
                id = domain.id,
                matchId = domain.matchId,
                teamMemberId = domain.teamMemberId,
                name = domain.name,
                role = TeamRole.fromDomain(domain.role),
                subTeam = SubTeam.fromDomain(domain.subTeam),
                createdTime = domain.createdTime,
                profileUrl = domain.profileUrl
            )
        }

        fun toDomain(presentation: MatchParticipantState): com.futsalgg.app.domain.match.model.MatchParticipant {
            return com.futsalgg.app.domain.match.model.MatchParticipant(
                id = presentation.id,
                matchId = presentation.matchId,
                teamMemberId = presentation.teamMemberId,
                name = presentation.name,
                role = TeamRole.toDomain(presentation.role),
                subTeam = SubTeam.toDomain(presentation.subTeam),
                createdTime = presentation.createdTime,
                profileUrl = presentation.profileUrl
            )
        }
    }
}