package com.futsalgg.app.presentation.match.creatematchparticipants

data class CreateMatchParticipantsState(
    val matchId: String = "",
    val teamMemberIds: List<String> = listOf()
)