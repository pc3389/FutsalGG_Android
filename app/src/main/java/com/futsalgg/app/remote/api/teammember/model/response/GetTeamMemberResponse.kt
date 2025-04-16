package com.futsalgg.app.remote.api.teammember.model.response

import com.futsalgg.app.remote.api.team.model.TeamRole
import com.google.gson.annotations.SerializedName

data class GetTeamMemberResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("createdTime")
    val createdTime: String,
    @SerializedName("team")
    val team: TeamInfo,
    @SerializedName("match")
    val match: MatchInfo
) {
    data class TeamInfo(
        @SerializedName("id")
        val id: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("role")
        val role: TeamRole
    )

    data class MatchInfo(
        @SerializedName("total")
        val total: Int,
        @SerializedName("history")
        val history: List<MatchHistory>
    ) {
        data class MatchHistory(
            @SerializedName("id")
            val id: String,
            @SerializedName("result")
            val result: MatchResult
        )
    }

    enum class MatchResult {
        @SerializedName("WIN")
        WIN,

        @SerializedName("LOSE")
        LOSE,

        @SerializedName("DRAW")
        DRAW
    }
}