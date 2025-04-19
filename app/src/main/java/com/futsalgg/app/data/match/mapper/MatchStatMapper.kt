package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.model.RoundStats
import com.futsalgg.app.remote.api.match.model.response.MatchStat as RemoteMatchStat

fun RemoteMatchStat.toDomain(): MatchStat {
    return MatchStat(
        id = id,
        matchParticipantId = matchParticipantId,
        roundNumber = roundNumber,
        statType = when (statType) {
            "GOAL" -> MatchStat.StatType.GOAL
            "ASSIST" -> MatchStat.StatType.ASSIST
            else -> throw IllegalArgumentException("Unknown stat type: $statType")
        },
        assistedMatchStatId = assistedMatchStatId,
        historyTime = historyTime,
        createdTime = createdTime
    )
}

fun Map<String, Map<String, List<List<RemoteMatchStat>>>>.toDomain(): List<RoundStats> {
    return this.map { (roundNumber, teamStats) ->
        RoundStats(
            roundNumber = roundNumber.toInt(),
            teamAStats = teamStats["A"]?.map { stats -> stats.map { it.toDomain() } } ?: emptyList(),
            teamBStats = teamStats["B"]?.map { stats -> stats.map { it.toDomain() } } ?: emptyList()
        )
    }.sortedBy { it.roundNumber }
} 