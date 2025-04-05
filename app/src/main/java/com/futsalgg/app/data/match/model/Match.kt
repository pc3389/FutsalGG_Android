package com.futsalgg.app.data.match.model

data class Match(
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