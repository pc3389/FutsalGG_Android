package com.futsalgg.app.data.team.repository

import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.remote.api.team.TeamApi
import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.domain.file.repository.OkHttpFileUploader
import com.futsalgg.app.domain.team.model.MyTeam
import com.futsalgg.app.domain.team.model.SearchTeamResponseModel
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.model.TeamRole
import com.futsalgg.app.remote.api.team.model.response.TeamRole as RemoteTeamRole
import java.io.File
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
                    Result.success(body.unique)
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
                            url = body.url,
                            uri = body.uri
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
                            url = body.url,
                            uri = body.uri
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
                DataError.UnknownError(
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
                Result.failure(
                    DataError.ServerError(
                        message = "팀 생성 실패: ${response.code()}",
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

    override suspend fun searchTeams(name: String): Result<SearchTeamResponseModel> {
        return try {
            val response = teamApi.searchTeams(
                accessToken = "Bearer $accessToken",
                name = name
            )
            Result.success(
                SearchTeamResponseModel(
                    teams = response.teams.map { remoteTeam ->
                        SearchTeamResponseModel.Team(
                            id = remoteTeam.id,
                            name = remoteTeam.name,
                            createdTime = remoteTeam.createdTime
                        )
                    }
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
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
                        id = body.id,
                        teamMemberId = body.teamMemberId,
                        name = body.name,
                        logoUrl = body.logoUrl,
                        role = when (body.role) {
                            RemoteTeamRole.OWNER -> TeamRole.OWNER
                            RemoteTeamRole.TEAM_LEADER -> TeamRole.TEAM_LEADER
                            RemoteTeamRole.TEAM_DEPUTY_LEADER -> TeamRole.TEAM_DEPUTY_LEADER
                            RemoteTeamRole.TEAM_SECRETARY -> TeamRole.TEAM_SECRETARY
                            RemoteTeamRole.TEAM_MEMBER -> TeamRole.TEAM_MEMBER
                        },
                        createdTime = body.createdTime
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