package com.futsalgg.app.data.model.response

import com.google.gson.annotations.SerializedName

data class CheckTeamNicknameResponse(
    @SerializedName("unique")
    val unique: Boolean
) 