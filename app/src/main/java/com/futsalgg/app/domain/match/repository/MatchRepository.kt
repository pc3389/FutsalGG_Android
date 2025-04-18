package com.futsalgg.app.domain.match.repository

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.common.model.MatchType

interface MatchRepository {
    suspend fun getMatches(
        accessToken: String,
        page: Int = 1,
        size: Int = 20
    ): Result<List<Match>>

    suspend fun getMatch(
        accessToken: String,
        id: String
    ): Result<Match>

    suspend fun deleteMatch(
        accessToken: String,
        id: String
    ): Result<Unit>

    suspend fun updateMatchRounds(
        accessToken: String,
        matchId: String,
        rounds: Int
    ): Result<Unit>

    suspend fun createMatch(
        accessToken: String,
        teamId: String,
        matchDate: String,
        type: MatchType,
        location: String,
        startTime: String? = null,
        endTime: String? = null,
        opponentTeamName: String? = null,
        substituteTeamMemberId: String? = null,
        description: String? = null,
        isVote: Boolean
    ): Result<Match>
} 