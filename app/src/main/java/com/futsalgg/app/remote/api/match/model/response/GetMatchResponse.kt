package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class GetMatchResponse(
    @SerializedName("id")
    val id: String,
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
    @SerializedName("opponentTeamName")
    val opponentTeamName: String?,
    @SerializedName("mom")
    val mom: Mom?,
    @SerializedName("createdTime")
    val createdTime: String
) {
    data class Mom(
        @SerializedName("profileUrl")
        val profileUrl: String?,
        @SerializedName("name")
        val name: String
    )
} 