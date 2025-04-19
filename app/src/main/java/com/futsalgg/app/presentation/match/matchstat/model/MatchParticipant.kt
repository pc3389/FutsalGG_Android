package com.futsalgg.app.presentation.match.matchstat.model

import com.futsalgg.app.domain.match.model.MatchParticipant as DomainMatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam as DomainSubTeam

data class MatchParticipant(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: String,
    val subTeam: SubTeam,
    val createdTime: String,
    val profilUrl: String
) {
    enum class SubTeam {
        NONE,
        A,
        B;

        companion object {
            fun fromDomain(domainType: DomainSubTeam): SubTeam {
                return when (domainType) {
                    DomainSubTeam.NONE -> NONE
                    DomainSubTeam.A -> A
                    DomainSubTeam.B -> B
                }
            }

            fun toDomain(presentationType: SubTeam): DomainSubTeam {
                return when (presentationType) {
                    NONE -> DomainSubTeam.NONE
                    A -> DomainSubTeam.A
                    B -> DomainSubTeam.B
                }
            }
        }
    }

    companion object {
        fun fromDomain(domain: DomainMatchParticipant): MatchParticipant {
            return MatchParticipant(
                id = domain.id,
                matchId = domain.matchId,
                teamMemberId = domain.teamMemberId,
                name = domain.name,
                role = domain.role,
                subTeam = SubTeam.fromDomain(domain.subTeam),
                createdTime = domain.createdTime,
                profilUrl = domain.profileUrl
            )
        }

        fun toDomain(presentation: MatchParticipant): DomainMatchParticipant {
            return DomainMatchParticipant(
                id = presentation.id,
                matchId = presentation.matchId,
                teamMemberId = presentation.teamMemberId,
                name = presentation.name,
                role = presentation.role,
                subTeam = SubTeam.toDomain(presentation.subTeam),
                createdTime = presentation.createdTime,
                profileUrl =  presentation.profilUrl
            )
        }
    }
} 