package com.futsalgg.app.remote.api.common

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: T
)