package com.futsalgg.app.presentation.team.model

enum class MatchType {
    INTRA_SQUAD,
    INTER_TEAM,
    ALL;

    override fun toString(): String {
        return when (this) {
            INTRA_SQUAD -> "자체전 (내전)"
            INTER_TEAM -> "매치전 (VS)"
            ALL -> "모두"
        }
    }

    companion object {
        fun fromDomain(domainType: com.futsalgg.app.domain.team.model.MatchType): MatchType {
            return when (domainType) {
                com.futsalgg.app.domain.team.model.MatchType.ALL -> ALL
                com.futsalgg.app.domain.team.model.MatchType.INTRA_SQUAD -> INTRA_SQUAD
                com.futsalgg.app.domain.team.model.MatchType.INTER_TEAM -> INTER_TEAM
            }
        }

        fun toDomain(presentationType: MatchType): com.futsalgg.app.domain.team.model.MatchType {
            return when (presentationType) {
                ALL -> com.futsalgg.app.domain.team.model.MatchType.ALL
                INTRA_SQUAD -> com.futsalgg.app.domain.team.model.MatchType.INTRA_SQUAD
                INTER_TEAM -> com.futsalgg.app.domain.team.model.MatchType.INTER_TEAM
            }
        }
    }
} 