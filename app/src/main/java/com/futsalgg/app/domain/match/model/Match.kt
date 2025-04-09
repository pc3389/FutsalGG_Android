package com.futsalgg.app.domain.match.model

import com.futsalgg.app.domain.common.model.MatchType

data class Match (
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