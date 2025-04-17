package com.futsalgg.app.data.match.repository

import com.futsalgg.app.domain.match.model.MatchParticipant
import com.futsalgg.app.domain.match.model.SubTeam
import com.futsalgg.app.domain.match.repository.MatchParticipantRepository
import com.futsalgg.app.remote.api.match.MatchApi
import com.futsalgg.app.remote.api.match.model.request.CreateMatchParticipantsRequest
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
                            MatchParticipant(
                                id = participant.id,
                                matchId = participant.matchId,
                                teamMemberId = participant.teamMemberId,
                                name = participant.name,
                                role = participant.role,
                                subTeam = when (participant.subTeam) {
                                    "NONE" -> SubTeam.NONE
                                    "A" -> SubTeam.A
                                    "B" -> SubTeam.B
                                    else -> SubTeam.NONE
                                },
                                createdTime = participant.createdTime
                            )
                        }
                    )
                } ?: Result.failure(IOException("응답이 비어있습니다"))
            } else {
                Result.failure(IOException("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 