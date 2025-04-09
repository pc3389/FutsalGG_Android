package com.futsalgg.app.presentation.match.model

import com.futsalgg.app.domain.match.model.MatchStatus as DomainMatchStatus

enum class MatchStatus{
    DRAFT,
    ONGOING,
    COMPLETED,
    CANCELLED;

    companion object {
        fun fromDomain(domainType: DomainMatchStatus): MatchStatus {
            return when (domainType) {
                DomainMatchStatus.DRAFT -> DRAFT
                DomainMatchStatus.ONGOING -> ONGOING
                DomainMatchStatus.COMPLETED -> COMPLETED
                DomainMatchStatus.CANCELLED -> CANCELLED
            }
        }

        fun toDomain(presentationType: MatchStatus): DomainMatchStatus {
            return when (presentationType) {
                DRAFT -> DomainMatchStatus.DRAFT
                ONGOING -> DomainMatchStatus.ONGOING
                COMPLETED -> DomainMatchStatus.COMPLETED
                CANCELLED -> DomainMatchStatus.CANCELLED
            }
        }
    }
}
