package com.futsalgg.app.domain.match.repository

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam

interface MatchParticipantRepository {
    suspend fun createMatchParticipants(
        accessToken: String,
        matchId: String,
        teamMemberIds: List<String>
    ): Result<List<MatchParticipant>>

    suspend fun updateMatchParticipantsSubTeam(
        accessToken: String,
        matchId: String,
        participantIds: List<String>,
        subTeam: SubTeam
    ): Result<Unit>
} 