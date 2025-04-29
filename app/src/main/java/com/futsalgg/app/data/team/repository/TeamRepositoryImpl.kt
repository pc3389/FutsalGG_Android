package com.futsalgg.app.data.team.repository

import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.remote.api.team.TeamApi
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.domain.team.model.MyTeam
import com.futsalgg.app.domain.team.model.SearchTeamResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.model.TeamRole
import com.futsalgg.app.remote.api.common.ApiResponse
import com.google.gson.Gson
import java.io.File
import com.futsalgg.app.remote.api.team.model.TeamRole as RemoteTeamRole
import java.io.IOException
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamApi: TeamApi,
    private val okHttpFileUploader: OkHttpFileUploader,
    private val accessToken: String
) : TeamRepository {

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            val response = teamApi.checkTeamNickname(nickname)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body.data.unique)
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[isTeamNicknameUnique] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
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

    override suspend fun getTeamLogoPresignedUrl(
        accessToken: String
    ): Result<TeamLogoPresignedUrlResponseModel> {
        return try {
            val response = teamApi.getTeamLogoPresignedUrl(
                authHeader = "Bearer $accessToken"
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        TeamLogoPresignedUrlResponseModel(
                            url = body.data.url,
                            uri = body.data.uri
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[getTeamLogoPresignedUrl] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[getTeamLogoPresignedUrl] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[getTeamLogoPresignedUrl] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        uri: String
    ): Result<TeamLogoResponseModel> {
        return try {
            val response = teamApi.updateTeamLogo(
                authHeader = "Bearer $accessToken",
                request = UpdateTeamLogoRequest(uri)
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        TeamLogoResponseModel(
                            url = body.data.url,
                            uri = body.data.uri
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[updateTeamLogo] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[updateTeamLogo] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[updateTeamLogo] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun uploadLogoImage(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel> {
        return try {
            // 1. presigned URL 획득
            val presignedResult = getTeamLogoPresignedUrl(accessToken)
            val presignedResponse = presignedResult.getOrElse { return Result.failure(it) }

            // 2. presigned URL로 파일 업로드
            val uploadResult =
                okHttpFileUploader.uploadFileToPresignedUrl(presignedResponse.url, file)
            uploadResult.getOrElse { return Result.failure(it) }

            // 3. 업로드 성공 후, updateProfile API 호출
            updateTeamLogo(accessToken, presignedResponse.uri)
        } catch (e: Exception) {
            Result.failure(
                DomainError.UnknownError(
                    message = "프로필 이미지 업로드 실패: ${e.message}",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: String,
        access: String,
        dues: Int
    ): Result<Unit> {
        return try {
            val response = teamApi.createTeam(
                authHeader = "Bearer $accessToken",
                request = CreateTeamRequest(
                    name = name,
                    introduction = introduction,
                    rule = rule,
                    matchType = matchType,
                    access = access,
                    dues = dues
                )
            )
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[createTeam] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[createTeam] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun searchTeams(name: String): Result<SearchTeamResponseModel> {
        return try {
            val response = teamApi.searchTeams(
                accessToken = "Bearer $accessToken",
                name = name
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        SearchTeamResponseModel(
                            teams = body.data.teams.map { remoteTeam ->
                                SearchTeamResponseModel.Team(
                                    id = remoteTeam.id,
                                    name = remoteTeam.name,
                                    createdTime = remoteTeam.createdTime,
                                    leaderName = remoteTeam.leaderName,
                                    memberCount = remoteTeam.memberCount
                                )
                            }
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "[searchTeams] 서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "[searchTeams] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: Exception) {
            Result.failure(
                DomainError.NetworkError(
                    message = "[searchTeams] 네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getMyTeam(accessToken: String): Result<MyTeam> = try {
        val response = teamApi.getMyTeam(
            accessToken = "Bearer $accessToken"
        )

        if (response.isSuccessful) {
            response.body()?.let { body ->
                Result.success(
                    MyTeam(
                        id = body.data.id,
                        teamMemberId = body.data.teamMemberId,
                        name = body.data.name,
                        introduction = body.data.introduction,
                        rule = body.data.rule,
                        logoUrl = body.data.logoUrl ?: "",
                        role = when (body.data.role) {
                            RemoteTeamRole.OWNER -> TeamRole.OWNER
                            RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                            RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                            RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                            RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                        },
                        createdTime = body.data.createdTime,
                        access = when (body.data.access) {
                            RemoteTeamRole.OWNER -> TeamRole.OWNER
                            RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                            RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                            RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                            RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                        }
                    )
                )
            } ?: Result.failure(
                DomainError.ServerError(
                    message = "[getMyTeam] 서버 응답이 비어있습니다.",
                    code = response.code()
                ) as Throwable
            )
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            if (response.body()?.message == "NOT_FOUND_TEAM_ID") {
                Result.failure(
                    DomainError.ServerError(
                        message = "NOT_FOUND_TEAM_ID",
                        code = response.code()
                    ) as Throwable
                )
            } else {

                Result.failure(
                    DomainError.ServerError(
                        message = "[getMyTeam] 서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[getMyTeam] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }

    override suspend fun acceptTeamMember(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit> = try {
        val response = teamApi.acceptTeamMember(
            accessToken = "Bearer $accessToken",
            teamMemberId = teamMemberId
        )
        
        if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            val errorBody = response.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

            Result.failure(
                DomainError.ServerError(
                    message = "[acceptTeamMember] 서버 오류: ${errorResponse.message}",
                    code = response.code()
                ) as Throwable
            )
        }
    } catch (e: IOException) {
        Result.failure(
            DomainError.NetworkError(
                message = "[acceptTeamMember] 네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable
        )
    }
} 