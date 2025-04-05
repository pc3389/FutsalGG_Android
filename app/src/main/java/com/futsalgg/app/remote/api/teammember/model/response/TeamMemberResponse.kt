package com.futsalgg.app.remote.api.teammember.model.response

data class TeamMemberResponse(
    val id: String,
    val name: String,
    val createdTime: String
)

data class GetTeamMembersResponse(
    val members: List<TeamMemberResponse>
) 