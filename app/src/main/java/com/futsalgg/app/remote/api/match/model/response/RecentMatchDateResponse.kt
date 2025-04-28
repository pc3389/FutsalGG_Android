package com.futsalgg.app.remote.api.match.model.response

import com.google.gson.annotations.SerializedName

data class RecentMatchDateResponse(
    @SerializedName("matchDate")
    val matchDate: String
) 