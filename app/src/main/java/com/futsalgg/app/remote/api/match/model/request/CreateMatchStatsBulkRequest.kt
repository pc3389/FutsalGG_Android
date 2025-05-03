package com.futsalgg.app.remote.api.match.model.request

data class CreateMatchStatsBulkRequest(
    val matchId: String,
    val stats: List<MatchStatRequest>
)

data class MatchStatRequest(
    val roundNumber: Int,
    val subTeam: String,
    val goalMatchParticipantId: String,
    val assistMatchParticipantId: String
) 