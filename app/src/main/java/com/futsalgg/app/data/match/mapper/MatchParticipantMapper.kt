package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.remote.api.match.model.response.MatchParticipant as RemoteMatchParticipant

fun RemoteMatchParticipant.toDomain(): MatchParticipant {
    return MatchParticipant(
        id = id,
        matchId = matchId,
        teamMemberId = teamMemberId,
        name = name,
        role = role,
        subTeam = when (subTeam) {
            "NONE" -> SubTeam.NONE
            "A" -> SubTeam.A
            "B" -> SubTeam.B
            else -> SubTeam.NONE
        },
        createdTime = createdTime
    )
} 