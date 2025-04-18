package com.futsalgg.app.remote.api.match.model.request

import com.google.gson.annotations.SerializedName

data class UpdateMatchParticipantsSubTeamRequest(
    @SerializedName("matchId")
    val matchId: String,
    @SerializedName("ids")
    val ids: List<String>,
    @SerializedName("subTeam")
    val subTeam: String
) 