package com.futsalgg.app.remote.api.match.model.request

import com.google.gson.annotations.SerializedName

data class UpdateMatchRoundsRequest(
    @SerializedName("rounds")
    val rounds: Int
) 