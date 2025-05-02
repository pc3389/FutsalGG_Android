package com.futsalgg.app.domain.match.model

data class UpdateMatch(
    val id: String,
    val substituteTeamMemberId: String?,
    val opponentTeamName: String?,
    val description: String,
    val rounds: Int,
    val type: String,
    val matchDate: String,
    val startTime: String?,
    val endTime: String?,
    val location: String,
    val voteStatus: String,
    val status: String,
    val createdTime: String
) 