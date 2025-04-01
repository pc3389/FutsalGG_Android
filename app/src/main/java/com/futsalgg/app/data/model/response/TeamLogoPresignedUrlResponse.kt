package com.futsalgg.app.data.model.response

import com.google.gson.annotations.SerializedName

data class TeamLogoPresignedUrlResponse(
    @SerializedName("url")
    val url: String,
    @SerializedName("uri")
    val uri: String
) 