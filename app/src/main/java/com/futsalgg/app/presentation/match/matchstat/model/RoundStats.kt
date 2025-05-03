package com.futsalgg.app.presentation.match.matchstat.model

import com.futsalgg.app.domain.match.model.RoundStats as DomainRoundStats
import com.futsalgg.app.domain.match.model.TeamStats as DomainTeamStats

data class TeamStats(
    val goal: MatchStat?,
    val assist: MatchStat?
)

data class RoundStats(
    val roundNumber: Int,
    val teamAStats: List<TeamStats>,
    val teamBStats: List<TeamStats>
) {
    companion object {
        fun fromDomain(domain: DomainRoundStats): RoundStats {
            return RoundStats(
                roundNumber = domain.roundNumber,
                teamAStats = domain.teamAStats.map { stats ->
                    TeamStats(
                        goal = stats.goal?.let { MatchStat.fromDomain(it) },
                        assist = stats.assist?.let { MatchStat.fromDomain(it) }
                    )
                },
                teamBStats = domain.teamBStats.map { stats ->
                    TeamStats(
                        goal = stats.goal?.let { MatchStat.fromDomain(it) },
                        assist = stats.assist?.let { MatchStat.fromDomain(it) }
                    )
                }
            )
        }

        fun toDomain(presentation: RoundStats): DomainRoundStats {
            return DomainRoundStats(
                roundNumber = presentation.roundNumber,
                teamAStats = presentation.teamAStats.map { stats ->
                    DomainTeamStats(
                        goal = stats.goal?.let { MatchStat.toDomain(it) },
                        assist = stats.assist?.let { MatchStat.toDomain(it) }
                    )
                },
                teamBStats = presentation.teamBStats.map { stats ->
                    DomainTeamStats(
                        goal = stats.goal?.let { MatchStat.toDomain(it) },
                        assist = stats.assist?.let { MatchStat.toDomain(it) }
                    )
                }
            )
        }
    }
} 