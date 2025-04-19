package com.futsalgg.app.domain.match.model

enum class SubTeam {
    NONE, A, B
}

data class MatchParticipant(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: String,
    val profileUrl: String,
    val subTeam: SubTeam,
    val createdTime: String
) 