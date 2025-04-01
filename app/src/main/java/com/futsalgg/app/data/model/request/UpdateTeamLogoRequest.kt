package com.futsalgg.app.data.model.request

import com.google.gson.annotations.SerializedName

data class UpdateTeamLogoRequest(
    @SerializedName("uri")
    val uri: String
) 