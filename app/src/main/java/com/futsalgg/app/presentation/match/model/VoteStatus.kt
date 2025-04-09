package com.futsalgg.app.presentation.match.model

import com.futsalgg.app.domain.match.model.VoteStatus as DomainVoteStatus

enum class VoteStatus{
    NONE,
    REGISTERED,
    ENDED;

    companion object{
        fun fromDomain(domainType: DomainVoteStatus): VoteStatus {
            return when (domainType) {
                DomainVoteStatus.NONE -> NONE
                DomainVoteStatus.ENDED -> ENDED
                DomainVoteStatus.REGISTERED -> REGISTERED
            }
        }

        fun toDomain(presentationType: VoteStatus): DomainVoteStatus {
            return when (presentationType) {
                NONE -> DomainVoteStatus.NONE
                ENDED -> DomainVoteStatus.ENDED
                REGISTERED -> DomainVoteStatus.REGISTERED
            }
        }
    }
}
