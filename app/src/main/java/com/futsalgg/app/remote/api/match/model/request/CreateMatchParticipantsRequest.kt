package com.futsalgg.app.remote.api.match.model.request

import com.google.gson.annotations.SerializedName

data class CreateMatchParticipantsRequest(
    @SerializedName("matchId")
    val matchId: String,
    @SerializedName("teamMemberIds")
    val teamMemberIds: List<String>
) 