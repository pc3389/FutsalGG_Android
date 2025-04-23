package com.futsalgg.app.remote.api.user.model.response

import com.futsalgg.app.remote.api.team.model.response.Gender
import com.google.gson.annotations.SerializedName

data class GetMyProfileResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("squadNumber")
    val squadNumber: Int?,
    @SerializedName("notification")
    val notification: Boolean,
    @SerializedName("profileUrl")
    val profileUrl: String?,
    @SerializedName("createdTime")
    val createdTime: String
) 