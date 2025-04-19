package com.futsalgg.app.presentation.match.matchstat.model

import com.futsalgg.app.domain.match.model.MatchStat as DomainMatchStat

data class MatchStat(
    val id: String = "",
    val matchParticipantId: String = "",
    val roundNumber: Int = 0,
    val statType: StatType = StatType.GOAL,
    val assistedMatchStatId: String? = "",
    val historyTime: String = "",
    val createdTime: String = ""
) {
    enum class StatType {
        GOAL,
        ASSIST;

        companion object {
            fun fromDomain(domainType: DomainMatchStat.StatType): StatType {
                return when (domainType) {
                    DomainMatchStat.StatType.GOAL -> GOAL
                    DomainMatchStat.StatType.ASSIST -> ASSIST
                }
            }

            fun toDomain(presentationType: StatType): DomainMatchStat.StatType {
                return when (presentationType) {
                    GOAL -> DomainMatchStat.StatType.GOAL
                    ASSIST -> DomainMatchStat.StatType.ASSIST
                }
            }
        }
    }

    companion object {
        fun fromDomain(domain: DomainMatchStat): MatchStat {
            return MatchStat(
                id = domain.id,
                matchParticipantId = domain.matchParticipantId,
                roundNumber = domain.roundNumber,
                statType = StatType.fromDomain(domain.statType),
                assistedMatchStatId = domain.assistedMatchStatId,
                historyTime = domain.historyTime,
                createdTime = domain.createdTime
            )
        }

        fun toDomain(presentation: MatchStat): DomainMatchStat {
            return DomainMatchStat(
                id = presentation.id,
                matchParticipantId = presentation.matchParticipantId,
                roundNumber = presentation.roundNumber,
                statType = StatType.toDomain(presentation.statType),
                assistedMatchStatId = presentation.assistedMatchStatId,
                historyTime = presentation.historyTime,
                createdTime = presentation.createdTime
            )
        }
    }
} 