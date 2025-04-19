package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class MatchParticipant(
    @SerializedName("id")
    val id: String,
    @SerializedName("matchId")
    val matchId: String,
    @SerializedName("teamMemberId")
    val teamMemberId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileUrl")
    val profileUrl: String?,
    @SerializedName("subTeam")
    val subTeam: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("createdTime")
    val createdTime: String
)