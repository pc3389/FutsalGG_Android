package com.futsalgg.app.presentation.match.matchstat.model

import com.futsalgg.app.domain.match.model.RoundStats as DomainRoundStats

data class RoundStats(
    val roundNumber: Int,
    val teamAStats: List<List<MatchStat>>,
    val teamBStats: List<List<MatchStat>>
) {
    companion object {
        fun fromDomain(domain: DomainRoundStats): RoundStats {
            return RoundStats(
                roundNumber = domain.roundNumber,
                teamAStats = domain.teamAStats.map { stats -> stats.map { MatchStat.fromDomain(it) } },
                teamBStats = domain.teamBStats.map { stats -> stats.map { MatchStat.fromDomain(it) } }
            )
        }

        fun toDomain(presentation: RoundStats): DomainRoundStats {
            return DomainRoundStats(
                roundNumber = presentation.roundNumber,
                teamAStats = presentation.teamAStats.map { stats -> stats.map { MatchStat.toDomain(it) } },
                teamBStats = presentation.teamBStats.map { stats -> stats.map { MatchStat.toDomain(it) } }
            )
        }
    }
} 