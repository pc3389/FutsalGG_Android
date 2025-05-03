package com.futsalgg.app.domain.match.model

data class MatchStat(
    val id: String,
    val matchParticipantId: String,
    val roundNumber: Int,
    val statType: StatType,
    val assistedMatchStatId: String?,
    val historyTime: String,
    val createdTime: String
) {
    enum class StatType {
        GOAL,
        ASSIST
    }
}

data class TeamStats(
    val goal: MatchStat?,
    val assist: MatchStat?
)

data class RoundStats(
    val roundNumber: Int,
    val teamAStats: List<TeamStats>,
    val teamBStats: List<TeamStats>
) 