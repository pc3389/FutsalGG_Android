package com.futsalgg.app.remote.api.user.model.request

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("squadNumber")
    val squadNumber: Int? = null
) 