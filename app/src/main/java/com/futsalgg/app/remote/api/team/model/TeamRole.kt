package com.futsalgg.app.remote.api.team.model

import com.google.gson.annotations.SerializedName

enum class TeamRole {
    @SerializedName("OWNER")
    OWNER,
    @SerializedName("TEAM_LEADER")
    TEAM_LEADER,
    @SerializedName("TEAM_DEPUTY_LEADER")
    TEAM_DEPUTY_LEADER,
    @SerializedName("TEAM_SECRETARY")
    TEAM_SECRETARY,
    @SerializedName("TEAM_MEMBER")
    TEAM_MEMBER
}