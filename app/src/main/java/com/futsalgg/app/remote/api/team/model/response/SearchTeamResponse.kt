package com.futsalgg.app.remote.api.team.model.response

import com.google.gson.annotations.SerializedName

data class SearchTeamResponse(
    @SerializedName("teams")
    val teams: List<Team>
)

data class Team(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("leaderName")
    val leaderName: String,
    @SerializedName("memberCount")
    val memberCount: String,
    @SerializedName("createdTime")
    val createdTime: String
) 