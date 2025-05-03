package com.futsalgg.app.data.team.repository

import com.futsalgg.app.data.team.mapper.toDomain
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.mapper.RoleMapper
import com.futsalgg.app.domain.team.model.TeamMember
import com.futsalgg.app.domain.team.model.TeamMemberProfile
import com.futsalgg.app.domain.team.model.TeamRole
import com.futsalgg.app.domain.team.repository.TeamMemberRepository
import com.futsalgg.app.remote.api.common.ApiResponse
import com.futsalgg.app.remote.api.team.model.TeamRole as RemoteTeamRole
import com.futsalgg.app.remote.api.team.model.request.JoinTeamRequest
import com.futsalgg.app.remote.api.team.TeamMemberApi
import com.google.gson.Gson
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
                Result.success(body.data.members?.map { it.toDomain(body.data.teamId) } ?: listOf())
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getTeamMembers] 서버 응답이 비어있습니다.",
                    code = response.code(),
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[getTeamMembers] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[getTeamMembers] 네트워크 연결을 확인해주세요.",
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
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[joinTeam] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[joinTeam] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun getMyTeamMember(
        accessToken: String
    ): Result<TeamMemberProfile> {
        return try {
            val response = teamMemberApi.getMyTeamMember("Bearer $accessToken")
            if (response.isSuccessful) {
                response.body()?.data?.let { data ->
                    Result.success(
                        TeamMemberProfile(
                            id = data.id,
                            name = data.name,
                            birthDate = data.birthDate,
                            generation = data.generation,
                            squadNumber = data.squadNumber,
                            profileUrl = data.profileUrl,
                            createdTime = data.createdTime,
                            team = TeamMemberProfile.TeamInfo(
                                id = data.team.id,
                                name = data.team.name,
                                role = RoleMapper.toDomain(data.team.role)
                            ),
                            match = TeamMemberProfile.MatchInfo(
                                total = data.match.total,
                                history = data.match.history?.map { history ->
                                    TeamMemberProfile.MatchInfo.MatchHistory(
                                        id = history.id,
                                        result = history.result.toDomain()
                                    )
                                }
                            )
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getMyTeamMember] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getMyTeamMember] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getMyTeamMember] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getTeamMemberWithId(
        accessToken: String,
        id: String
    ): Result<TeamMemberProfile> {
        return try {
            val response = teamMemberApi.getTeamMemberWithId("Bearer $accessToken", id)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        TeamMemberProfile(
                            id = body.data.id,
                            generation = body.data.generation,
                            squadNumber = body.data.squadNumber,
                            profileUrl = body.data.profileUrl,
                            name = body.data.name,
                            birthDate = body.data.birthDate,
                            createdTime = body.data.createdTime,
                            team = TeamMemberProfile.TeamInfo(
                                id = body.data.team.id,
                                name = body.data.team.name,
                                role = when (body.data.team.role) {
                                    RemoteTeamRole.OWNER -> TeamRole.OWNER
                                    RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                                    RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                                    RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                                    RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                                }
                            ),
                            match = TeamMemberProfile.MatchInfo(
                                total = body.data.match.total,
                                history = body.data.match.history?.map { history ->
                                    TeamMemberProfile.MatchInfo.MatchHistory(
                                        id = history.id,
                                        result = history.result.toDomain()
                                    )
                                }
                            ),
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getTeamMemberWithId] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getTeamMemberWithId] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getTeamMemberWithId] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getTeamMembersByTeamId(
        accessToken: String,
        teamId: String
    ): Result<List<TeamMember>> = try {
        val response = teamMemberApi.getTeamMembersByTeamId("Bearer $accessToken", teamId)
        if (response.isSuccessful) {
            response.body()?.data?.let { data ->
                Result.success(data.members?.map { it.toDomain(data.teamId) } ?: listOf())
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getTeamMembersByTeamId] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[getTeamMembersByTeamId] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: Exception) {
        Result.failure(
            DomainError.UnknownError(
                message = "[getTeamMembersByTeamId] 알 수 없는 오류가 발생했습니다: ${e.message}",
                cause = e
            ) as Throwable
        )
    }
} 