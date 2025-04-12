package com.futsalgg.app.remote.api.user.model.request

import com.google.gson.annotations.SerializedName

data class UpdateNotificationRequest(
    @SerializedName("notification")
    val notification: Boolean
) 