package com.futsalgg.app.presentation.common.model

import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType

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
        fun fromDomain(domainType: DomainMatchType): MatchType {
            return when (domainType) {
                DomainMatchType.ALL -> ALL
                DomainMatchType.INTRA_SQUAD -> INTRA_SQUAD
                DomainMatchType.INTER_TEAM -> INTER_TEAM
            }
        }

        fun toDomain(presentationType: MatchType): DomainMatchType {
            return when (presentationType) {
                ALL -> DomainMatchType.ALL
                INTRA_SQUAD -> DomainMatchType.INTRA_SQUAD
                INTER_TEAM -> DomainMatchType.INTER_TEAM
            }
        }
    }
}