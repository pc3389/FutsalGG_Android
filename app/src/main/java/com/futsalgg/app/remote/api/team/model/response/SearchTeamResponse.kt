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
    @SerializedName("createdTime")
    val createdTime: String
) 