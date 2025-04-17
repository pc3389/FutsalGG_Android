package com.futsalgg.app.presentation.match.creatematchmember

data class MatchParticipantState(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: String,
    val profileUrl: String,
    val subTeam: SubTeam,
    val createdTime: String
)

enum class SubTeam {
    NONE, A, B
}