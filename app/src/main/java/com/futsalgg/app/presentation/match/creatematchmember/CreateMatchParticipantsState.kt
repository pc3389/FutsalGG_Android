package com.futsalgg.app.presentation.match.creatematchmember

data class CreateMatchParticipantsState(
    val matchId: String = "",
    val teamMemberIds: List<String> = listOf()
)