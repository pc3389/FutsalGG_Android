package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class CreateMatchParticipantsResponse(
    @SerializedName("participants")
    val participants: List<MatchParticipant>
)