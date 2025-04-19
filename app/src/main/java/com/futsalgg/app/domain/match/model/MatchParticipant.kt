package com.futsalgg.app.domain.match.model

import com.futsalgg.app.domain.team.model.TeamRole

enum class SubTeam {
    NONE, A, B
}

data class MatchParticipant(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: TeamRole,
    val profileUrl: String,
    val subTeam: SubTeam,
    val createdTime: String
) 