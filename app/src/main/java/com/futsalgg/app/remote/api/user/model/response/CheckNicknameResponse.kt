package com.futsalgg.app.remote.api.user.model.response

import com.google.gson.annotations.SerializedName

data class CheckNicknameResponse(
    @SerializedName("unique")
    val unique: Boolean
) 