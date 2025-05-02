package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class UpdateMatchResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("substituteTeamMemberId")
    val substituteTeamMemberId: String?,
    @SerializedName("opponentTeamName")
    val opponentTeamName: String?,
    @SerializedName("description")
    val description: String,
    @SerializedName("rounds")
    val rounds: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("matchDate")
    val matchDate: String,
    @SerializedName("startTime")
    val startTime: String?,
    @SerializedName("endTime")
    val endTime: String?,
    @SerializedName("location")
    val location: String,
    @SerializedName("voteStatus")
    val voteStatus: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("createdTime")
    val createdTime: String
) 