package com.futsalgg.app.data.teammember.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.data.teammember.mapper.toDomain
import com.futsalgg.app.domain.teammember.model.TeamMember
import com.futsalgg.app.domain.teammember.model.TeamMemberProfile
import com.futsalgg.app.domain.team.model.TeamRole
import com.futsalgg.app.domain.teammember.repository.TeamMemberRepository
import com.futsalgg.app.remote.api.team.model.TeamRole as RemoteTeamRole
import com.futsalgg.app.remote.api.team.model.request.JoinTeamRequest
import com.futsalgg.app.remote.api.teammember.TeamMemberApi
import java.io.IOException
import javax.inject.Inject

class TeamMemberRepositoryImpl @Inject constructor(
    private val teamMemberApi: TeamMemberApi,
) : TeamMemberRepository {

    override suspend fun getTeamMembers(
        accessToken: String,
        name: String,
        role: String
    ): Result<List<TeamMember>> = try {
        val response = teamMemberApi.getTeamMembers(
            authHeader = "Bearer $accessToken",
            name = name,
            role = role
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(body.members.map { it.toDomain() })
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

    override suspend fun joinTeam(
        accessToken: String,
        teamId: String
    ): Result<Unit> = try {
        val response = teamMemberApi.joinTeam(
            accessToken = "Bearer $accessToken",
            request = JoinTeamRequest(teamId)
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

    override suspend fun getMyTeamMember(
        accessToken: String
    ): Result<TeamMemberProfile> {
        return try {
            val response = teamMemberApi.getMyTeamMember(accessToken)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        TeamMemberProfile(
                            name = body.name,
                            birthDate = body.birthDate,
                            createdTime = body.createdTime,
                            team = TeamMemberProfile.TeamInfo(
                                id = body.team.id,
                                name = body.team.name,
                                role = when (body.team.role) {
                                    RemoteTeamRole.OWNER -> TeamRole.OWNER
                                    RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                                    RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                                    RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                                    RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                                }
                            ),
                            match = TeamMemberProfile.MatchInfo(
                                total = body.match.total,
                                history = body.match.history.map { history ->
                                    TeamMemberProfile.MatchInfo.MatchHistory(
                                        id = history.id,
                                        result = history.result.toDomain()
                                    )
                                }
                            )
                        )
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

    override suspend fun getTeamMember(accessToken: String, id: String): Result<TeamMemberProfile> {
        return try {
            val response = teamMemberApi.getTeamMember(accessToken, id)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        TeamMemberProfile(
                            name = body.name,
                            birthDate = body.birthDate,
                            createdTime = body.createdTime,
                            team = TeamMemberProfile.TeamInfo(
                                id = body.team.id,
                                name = body.team.name,
                                role = when (body.team.role) {
                                    RemoteTeamRole.OWNER -> TeamRole.OWNER
                                    RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                                    RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                                    RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                                    RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                                }
                            ),
                            match = TeamMemberProfile.MatchInfo(
                                total = body.match.total,
                                history = body.match.history.map { history ->
                                    TeamMemberProfile.MatchInfo.MatchHistory(
                                        id = history.id,
                                        result = history.result.toDomain()
                                    )
                                }
                            )
                        )
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
} 