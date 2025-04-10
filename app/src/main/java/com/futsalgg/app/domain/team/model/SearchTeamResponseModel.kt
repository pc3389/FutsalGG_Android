package com.futsalgg.app.domain.team.model

data class SearchTeamResponseModel(
    val teams: List<Team>
) {
    data class Team(
        val id: String,
        val name: String,
        val createdTime: String
    )
} 