package com.futsalgg.app.remote.api.match.model.response

data class MatchResponse(
    val id: String,
    val opponentTeamName: String?,
    val description: String?,
    val type: MatchType,
    val matchDate: String,
    val startTime: String?,
    val endTime: String?,
    val location: String,
    val voteStatus: VoteStatus,
    val status: MatchStatus,
    val createdTime: String
)

enum class MatchType {
    INTRA_SQUAD,
    INTER_TEAM
}

enum class VoteStatus {
    NONE,
    REGISTERED,
    ENDED
}

enum class MatchStatus {
    DRAFT,
    ONGOING,
    COMPLETED,
    CANCELLED
} 