package com.futsalgg.app.presentation.team.jointeam

import com.futsalgg.app.presentation.team.jointeam.model.Team

data class JoinTeamState(
    val name: String = "",
    val buttonEnabled: Boolean = false,
    val searchResults: List<Team> = emptyList(),
    val selectedTeamId: String? = null
) 