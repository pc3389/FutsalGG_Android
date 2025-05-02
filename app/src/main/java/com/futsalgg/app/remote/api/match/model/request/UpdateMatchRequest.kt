package com.futsalgg.app.remote.api.match.model.request

import com.google.gson.annotations.SerializedName

data class UpdateMatchRequest(
    @SerializedName("id")
    val id: String,
    @SerializedName("matchDate")
    val matchDate: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("startTime")
    val startTime: String? = null,
    @SerializedName("endTime")
    val endTime: String? = null,
    @SerializedName("substituteTeamMemberId")
    val substituteTeamMemberId: String? = null
) 