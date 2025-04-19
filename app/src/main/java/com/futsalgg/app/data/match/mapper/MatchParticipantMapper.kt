package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.team.model.TeamRole
import com.futsalgg.app.remote.api.match.model.response.MatchParticipant as RemoteMatchParticipant

fun RemoteMatchParticipant.toDomain(): MatchParticipant {
    return MatchParticipant(
        id = id,
        matchId = matchId,
        teamMemberId = teamMemberId,
        name = name,
        role = when (role) {
            "OWNER" -> TeamRole.OWNER
            "TEAM_LEADER" -> TeamRole.TEAM_LEADER
            "TEAM_DEPUTY_LEADER" -> TeamRole.TEAM_DEPUTY_LEADER
            "TEAM_SECRETARY" -> TeamRole.TEAM_SECRETARY
            else ->TeamRole.TEAM_MEMBER
        },
        subTeam = when (subTeam) {
            "NONE" -> SubTeam.NONE
            "A" -> SubTeam.A
            "B" -> SubTeam.B
            else -> SubTeam.NONE
        },
        profileUrl = profileUrl ?: "",
        createdTime = createdTime
    )
} 