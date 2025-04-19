package com.futsalgg.app.data.match.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.data.match.mapper.toDomain
import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.model.RoundStats
import com.futsalgg.app.domain.match.repository.MatchRepository
import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
import com.futsalgg.app.remote.api.match.model.request.CreateMatchStatRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchRoundsRequest
import com.futsalgg.app.remote.api.match.model.response.MatchType as RemoteMatchType
import java.io.IOException
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchApi: MatchApi
) : MatchRepository {
    override suspend fun getMatches(
        accessToken: String,
        page: Int,
        size: Int
    ): Result<List<com.futsalgg.app.domain.match.model.Match>> = try {
        val response = matchApi.getMatches(
            authHeader = "Bearer $accessToken",
            page = page,
            size = size
        )
        
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.matches.map { it.toDomain() })
            } ?: Result.failure(
                DataError.ServerError(
                    message = "서버 응답이 비어있습니다.",
                    cause = null
                ) as Throwable
            )
        } else {
            Result.failure(
                DataError.ServerError(
                    message = "서버 오류: ${response.code()}",
                    cause = null
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DataError.NetworkError(
                message = "네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun getMatch(
        accessToken: String,
        id: String
    ): Result<com.futsalgg.app.domain.match.model.Match> = try {
        val response = matchApi.getMatch(
            authHeader = "Bearer $accessToken",
            id = id
        )
        
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.toDomain())
            } ?: Result.failure(
                DataError.ServerError(
                    message = "서버 응답이 비어있습니다.",
                    cause = null
                ) as Throwable
            )
        } else {
            Result.failure(
                DataError.ServerError(
                    message = "서버 오류: ${response.code()}",
                    cause = null
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DataError.NetworkError(
                message = "네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun deleteMatch(
        accessToken: String,
        id: String
    ): Result<Unit> = try {
        val response = matchApi.deleteMatch(
            authHeader = "Bearer $accessToken",
            id = id
        )
        
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(
                DataError.ServerError(
                    message = "서버 오류: ${response.code()}",
                    cause = null
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DataError.NetworkError(
                message = "네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun createMatch(
        accessToken: String,
        teamId: String,
        matchDate: String,
        type: DomainMatchType,
        location: String,
        startTime: String?,
        endTime: String?,
        opponentTeamName: String?,
        substituteTeamMemberId: String?,
        description: String?,
        isVote: Boolean
    ): Result<com.futsalgg.app.domain.match.model.Match> = try {
        val response = matchApi.createMatch(
            authHeader = "Bearer $accessToken",
            request = CreateMatchRequest(
                teamId = teamId,
                matchDate = matchDate,
                type = RemoteMatchType.valueOf(type.name),
                location = location,
                startTime = startTime,
                endTime = endTime,
                opponentTeamName = opponentTeamName,
                substituteTeamMemberId = substituteTeamMemberId,
                description = description,
                isVote = isVote
            )
        )
        
        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.toDomain())
            } ?: Result.failure(
                DataError.ServerError(
                    message = "서버 응답이 비어있습니다.",
                    cause = null
                ) as Throwable
            )
        } else {
            Result.failure(
                DataError.ServerError(
                    message = "서버 오류: ${response.code()}",
                    cause = null
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DataError.NetworkError(
                message = "네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun updateMatchRounds(
        accessToken: String,
        matchId: String,
        rounds: Int
    ): Result<Unit> {
        return try {
            val response = matchApi.updateMatchRounds(
                accessToken = "Bearer $accessToken",
                matchId = matchId,
                request = UpdateMatchRoundsRequest(rounds = rounds)
            )

            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 오류: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getMatchStats(
        accessToken: String,
        matchId: String
    ): Result<List<RoundStats>> {
        return try {
            val response = matchApi.getMatchStats(
                accessToken = "Bearer $accessToken",
                matchId = matchId
            )

            if (response.isSuccessful) {
                response.body()?.let { stats ->
                    Result.success(stats.stats.toDomain())
                } ?: Result.failure(IOException("응답이 비어있습니다."))
            } else {
                Result.failure(IOException("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createMatchStat(
        accessToken: String,
        matchParticipantId: String,
        roundNumber: Int,
        statType: MatchStat.StatType,
        assistedMatchStatId: String?
    ): Result<MatchStat> {
        return try {
            val response = matchApi.createMatchStat(
                accessToken = "Bearer $accessToken",
                request = CreateMatchStatRequest(
                    matchParticipantId = matchParticipantId,
                    roundNumber = roundNumber,
                    statType = statType.name,
                    assistedMatchStatId = assistedMatchStatId
                )
            )

            if (response.isSuccessful) {
                response.body()?.let { stat ->
                    Result.success(stat.toDomain())
                } ?: Result.failure(
                    DataError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        cause = null
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 오류: ${response.code()}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DataError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }
} 