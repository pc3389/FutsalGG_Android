package com.futsalgg.app.remote.api.team.model.response

import com.futsalgg.app.presentation.team.model.Access
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