package com.futsalgg.app.data.match.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.data.match.mapper.toData
import com.futsalgg.app.data.match.mapper.toDomain
import com.futsalgg.app.domain.match.repository.MatchRepository
import com.futsalgg.app.domain.common.model.MatchType as DomainMatchType
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchRequest
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
                Result.success(body.matches.map { it.toData().toDomain() })
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
                Result.success(body.toData().toDomain())
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