package com.futsalgg.app.data.match.repository

import com.futsalgg.app.data.match.mapper.toDomain
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchParticipantsRequest
import com.futsalgg.app.remote.api.match.model.request.UpdateMatchParticipantsSubTeamRequest
import com.google.gson.Gson
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
                        body.data.participants.map { participant ->
                            participant.toDomain()
                        }
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[createMatchParticipants] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[createMatchParticipants] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[createMatchParticipants] 네트워크 연결을 확인해주세요.",
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
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[updateMatchParticipantsSubTeam] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[updateMatchParticipantsSubTeam] 네트워크 연결을 확인해주세요.",
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
                    Result.success(participants.data.participants.map { it.toDomain() })
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getMatchParticipants] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getMatchParticipants] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getMatchParticipants] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }
} 