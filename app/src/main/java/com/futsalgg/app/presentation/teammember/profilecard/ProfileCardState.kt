package com.futsalgg.app.presentation.teammember.profilecard

import com.futsalgg.app.presentation.common.model.MatchResult
import com.futsalgg.app.presentation.common.model.TeamRole

data class ProfileCardState(
    val id: String,
    val name: String,
    val birthday: String,
    val squadNumber: String?,
    val profileUrl: String?,
    val generation: String,
    val role: TeamRole,
    val createdTime: String?,
    val teamName: String,
    val teamLogoUrl: String?,
    val totalGameNum: Int,
    val history: List<MatchHistory>
) {
    companion object {
        val Initial = ProfileCardState(
            id = "",
            name = "",
            birthday = "",
            squadNumber = null,
            profileUrl = null,
            generation = "",
            role = TeamRole.TEAM_MEMBER,
            createdTime = "",
            teamName = "",
            teamLogoUrl = "",
            totalGameNum = 0,
            history = listOf()
        )
    }
}

data class MatchHistory(
    val id: String,
    val result: MatchResult
)