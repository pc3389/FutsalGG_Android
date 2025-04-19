package com.futsalgg.app.remote.api.match.model.request

import com.google.gson.annotations.SerializedName

data class CreateMatchStatRequest(
    @SerializedName("matchParticipantId")
    val matchParticipantId: String,
    @SerializedName("roundNumber")
    val roundNumber: Int,
    @SerializedName("statType")
    val statType: String,
    @SerializedName("assistedMatchStatId")
    val assistedMatchStatId: String?
)