package com.futsalgg.app.remote.model.match

import com.google.gson.annotations.SerializedName

data class CreateMatchParticipantsResponse(
    @SerializedName("participants")
    val participants: List<MatchParticipant>
)

data class MatchParticipant(
    @SerializedName("id")
    val id: String,
    @SerializedName("matchId")
    val matchId: String,
    @SerializedName("teamMemberId")
    val teamMemberId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("subTeam")
    val subTeam: String,
    @SerializedName("createdTime")
    val createdTime: String
) 