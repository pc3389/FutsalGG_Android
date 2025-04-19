package com.futsalgg.app.remote.model.match

import com.google.gson.annotations.SerializedName

data class GetMatchParticipantsResponse(
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