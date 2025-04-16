package com.futsalgg.app.remote.api.team.model.response

import com.futsalgg.app.remote.api.team.model.TeamRole
import com.google.gson.annotations.SerializedName

data class GetMyTeamResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("teamMemberId")
    val teamMemberId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("logoUrl")
    val logoUrl: String,
    @SerializedName("role")
    val role: TeamRole,
    @SerializedName("createdTime")
    val createdTime: String,
    @SerializedName("access")
    val access: TeamRole
)

