package com.futsalgg.app.domain.match.repository

import com.futsalgg.app.domain.match.model.MatchParticipant

interface MatchParticipantRepository {
    suspend fun createMatchParticipants(
        accessToken: String,
        matchId: String,
        teamMemberIds: List<String>
    ): Result<List<MatchParticipant>>
} 