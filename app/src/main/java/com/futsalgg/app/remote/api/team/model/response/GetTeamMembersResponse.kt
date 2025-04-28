package com.futsalgg.app.remote.api.team.model.response

import com.futsalgg.app.remote.api.team.model.TeamRole
import com.google.gson.annotations.SerializedName

data class GetTeamMembersResponse(
    @SerializedName("teamId")
    val teamId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("logoUrl")
    val logoUrl: String?,
    @SerializedName("teamMembers")
    val members: List<TeamMemberDetailResponse>?
)

data class TeamMemberDetailResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("role")
    val role: TeamRole,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("generation")
    val generation: String,
    @SerializedName("profileUrl")
    val profileUrl: String?,
    @SerializedName("squadNumber")
    val squadNumber: Int,
    @SerializedName("gender")
    val gender: Gender,
    @SerializedName("status")
    val status: TeamMemberStatus,
    @SerializedName("createdTime")
    val createdTime: String
)

enum class Gender {
    @SerializedName("MAN")
    MAN,
    @SerializedName("WOMAN")
    WOMAN
}

enum class TeamMemberStatus {
    @SerializedName("PENDING")
    PENDING,
    @SerializedName("ACTIVE")
    ACTIVE,
    @SerializedName("INACTIVE")
    INACTIVE
} 