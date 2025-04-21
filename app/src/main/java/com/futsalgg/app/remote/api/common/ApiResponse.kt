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

data class ApiListResponse<T>(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<T>  // 배열을 받을 수 있도록 List로 변경
)