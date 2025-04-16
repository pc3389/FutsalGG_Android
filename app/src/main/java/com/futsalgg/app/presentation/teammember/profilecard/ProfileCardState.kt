package com.futsalgg.app.presentation.teammember.profilecard

import com.futsalgg.app.presentation.common.model.MatchResult
import com.futsalgg.app.presentation.main.model.TeamRole

data class ProfileCardState(
    val name: String,
    val birthday: String,
    val squadNumber: Int?,
    val profileUrl: String?,
    val role: TeamRole,
    val createdTime: String,
    val teamName: String,
    val teamLogoUrl: String?,
    val totalGameNum: Int,
    val history: List<MatchHistory>
) {
    companion object {
        val Initial = ProfileCardState(
            name = "",
            birthday = "",
            squadNumber = 0,
            profileUrl = null,
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