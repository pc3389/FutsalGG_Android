package com.futsalgg.app.remote.api.team.model.request

import com.google.gson.annotations.SerializedName

data class JoinTeamRequest(
    @SerializedName("team_id")
    val teamId: String
) 