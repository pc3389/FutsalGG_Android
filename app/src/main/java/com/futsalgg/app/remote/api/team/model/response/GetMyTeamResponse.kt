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
    @SerializedName("introduction")
    val introduction: String?,
    @SerializedName("rule")
    val rule: String,
    @SerializedName("logoUrl")
    val logoUrl: String?,
    @SerializedName("role")
    val role: TeamRole,
    @SerializedName("access")
    val access: TeamRole,
    @SerializedName("createdTime")
    val createdTime: String
)

