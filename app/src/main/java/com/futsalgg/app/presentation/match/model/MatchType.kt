package com.futsalgg.app.presentation.match.model

enum class MatchType {
    INTRA_SQUAD,
    INTER_TEAM;

    override fun toString(): String {
        return when (this) {
            INTRA_SQUAD -> "자체전 (내전)"
            INTER_TEAM -> "매치전 (VS)"
        }
    }

    companion object {
        fun fromDomain(domainType: com.futsalgg.app.domain.match.model.MatchType): MatchType {
            return when (domainType) {
                com.futsalgg.app.domain.match.model.MatchType.INTRA_SQUAD -> INTRA_SQUAD
                com.futsalgg.app.domain.match.model.MatchType.INTER_TEAM -> INTER_TEAM
            }
        }

        fun toDomain(presentationType: MatchType): com.futsalgg.app.domain.match.model.MatchType {
            return when (presentationType) {
                INTRA_SQUAD -> com.futsalgg.app.domain.match.model.MatchType.INTRA_SQUAD
                INTER_TEAM -> com.futsalgg.app.domain.match.model.MatchType.INTER_TEAM
            }
        }
    }
} 