package com.futsalgg.app.domain.match.model

data class CreateBulkMatchStat(
    val roundNumber: Int,
    val subTeam: String,
    val goalMatchParticipantId: String,
    val assistMatchParticipantId: String?
) 