package com.futsalgg.app.domain.match.repository

import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.model.RoundStats

interface MatchRepository {
    suspend fun getMatches(
        accessToken: String,
        page: Int = 1,
        size: Int = 20,
        teamId: String
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

    suspend fun getMatchStats(
        accessToken: String,
        matchId: String
    ): Result<List<RoundStats>>

    suspend fun createMatchStat(
        accessToken: String,
        matchParticipantId: String,
        roundNumber: Int,
        statType: MatchStat.StatType,
        assistedMatchStatId: String?
    ): Result<MatchStat>
} 