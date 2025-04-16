package com.futsalgg.app.domain.team.model

import com.futsalgg.app.domain.match.model.MatchResult


data class TeamMemberProfile(
    val name: String,
    val birthDate: String,
    val createdTime: String,
    val team: TeamInfo,
    val match: MatchInfo
) {
    data class TeamInfo(
        val id: String,
        val name: String,
        val role: TeamRole
    )

    data class MatchInfo(
        val total: Int,
        val history: List<MatchHistory>
    ) {
        data class MatchHistory(
            val id: String,
            val result: MatchResult
        )
    }
}