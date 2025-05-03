package com.futsalgg.app.data.match.mapper

import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.model.RoundStats
import com.futsalgg.app.remote.api.match.model.response.MatchStat as RemoteMatchStat
import com.futsalgg.app.remote.api.match.model.request.MatchStatRequest
import com.futsalgg.app.domain.match.model.CreateBulkMatchStat

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

object MatchStatMapper {
    fun toRequest(domain: CreateBulkMatchStat): MatchStatRequest {
        return MatchStatRequest(
            roundNumber = domain.roundNumber,
            subTeam = domain.subTeam,
            goalMatchParticipantId = domain.goalMatchParticipantId,
            assistMatchParticipantId = domain.assistMatchParticipantId
        )
    }

    fun toDomain(request: MatchStatRequest): CreateBulkMatchStat {
        return CreateBulkMatchStat(
            roundNumber = request.roundNumber,
            subTeam = request.subTeam,
            goalMatchParticipantId = request.goalMatchParticipantId,
            assistMatchParticipantId = request.assistMatchParticipantId
        )
    }
} 