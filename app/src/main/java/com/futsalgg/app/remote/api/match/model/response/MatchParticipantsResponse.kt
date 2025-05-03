package com.futsalgg.app.remote.api.match.model.response

data class MatchParticipantsResponse(
    val participants: List<MatchParticipant>,
    val participate: Boolean
) 