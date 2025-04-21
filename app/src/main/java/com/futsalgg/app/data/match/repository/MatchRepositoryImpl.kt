package com.futsalgg.app.data.match.repository

import com.futsalgg.app.data.match.mapper.toDomain
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.model.Match
import com.futsalgg.app.domain.match.model.MatchStat
import com.futsalgg.app.domain.match.model.RoundStats
import com.futsalgg.app.domain.match.repository.MatchRepository
import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
import com.futsalgg.app.remote.api.match.model.request.CreateMatchStatRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchRoundsRequest
import com.google.gson.Gson
import com.futsalgg.app.remote.api.match.model.response.MatchType as RemoteMatchType
import java.io.IOException
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
    private val matchApi: MatchApi
) : MatchRepository {
    override suspend fun getMatches(
        accessToken: String,
        page: Int,
        size: Int,
        teamId: String
    ): Result<List<Match>> = try {
        val response = matchApi.getMatches(
            authHeader = "Bearer $accessToken",
            page = page,
            size = size,
            teamId = teamId
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.data.map { it.toDomain() })
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getMatches] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[getMatches] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[getMatches] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun getMatch(
        accessToken: String,
        id: String
    ): Result<Match> = try {
        val response = matchApi.getMatch(
            authHeader = "Bearer $accessToken",
            id = id
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.data.toDomain())
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getMatch] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[getMatch] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[getMatch] 네트워크 연결을 확인해주세요.",
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
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[deleteMatch] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[deleteMatch] 네트워크 연결을 확인해주세요.",
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
    ): Result<Match> = try {
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
                Result.success(body.data.toDomain())
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[createMatch] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[createMatch] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[createMatch] 네트워크 연결을 확인해주세요.",
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
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[updateMatchRounds] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[updateMatchRounds] 네트워크 연결을 확인해주세요.",
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
                    Result.success(stats.data.stats.toDomain())
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getMatchStats] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getMatchStats] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getMatchStats] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
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
                    Result.success(stat.data.toDomain())
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[createMatchStat] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[createMatchStat] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[createMatchStat] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }
}