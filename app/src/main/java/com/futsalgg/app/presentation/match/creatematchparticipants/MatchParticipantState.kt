package com.futsalgg.app.presentation.match.creatematchparticipants

data class MatchParticipantState(
    val id: String,
    val matchId: String,
    val teamMemberId: String,
    val name: String,
    val role: String,
    val profileUrl: String,
    val subTeam: SubTeam,
    val isSelected: Boolean = false,
    val createdTime: String
)

enum class SubTeam {
    NONE, A, B
}