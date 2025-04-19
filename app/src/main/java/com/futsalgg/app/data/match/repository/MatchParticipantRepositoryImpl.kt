package com.futsalgg.app.data.match.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.data.match.mapper.toDomain
import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchParticipantsRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchParticipantsSubTeamRequest
import java.io.IOException
import javax.inject.Inject

class MatchParticipantRepositoryImpl @Inject constructor(
    private val matchApi: MatchApi
) : MatchParticipantRepository {
    override suspend fun createMatchParticipants(
        accessToken: String,
        matchId: String,
        teamMemberIds: List<String>
    ): Result<List<MatchParticipant>> {
        return try {
            val response = matchApi.createMatchParticipants(
                accessToken = "Bearer $accessToken",
                request = CreateMatchParticipantsRequest(
                    matchId = matchId,
                    teamMemberIds = teamMemberIds
                )
            )

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        body.participants.map { participant ->
                            participant.toDomain()
                        }
                    )
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

    override suspend fun updateMatchParticipantsSubTeam(
        accessToken: String,
        matchId: String,
        participantIds: List<String>,
        subTeam: SubTeam
    ): Result<Unit> {
        return try {
            val response = matchApi.updateMatchParticipantsSubTeam(
                accessToken = "Bearer $accessToken",
                request = UpdateMatchParticipantsSubTeamRequest(
                    matchId = matchId,
                    ids = participantIds,
                    subTeam = subTeam.name
                )
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

    override suspend fun getMatchParticipants(
        accessToken: String,
        matchId: String
    ): Result<List<MatchParticipant>> {
        return try {
            val response = matchApi.getMatchParticipants(
                accessToken = "Bearer $accessToken",
                matchId = matchId
            )

            if (response.isSuccessful) {
                response.body()?.let { participants ->
                    Result.success(participants.map { it.toDomain() })
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