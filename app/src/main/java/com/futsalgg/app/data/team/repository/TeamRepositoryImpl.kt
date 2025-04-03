package com.futsalgg.app.data.team.repository

import com.futsalgg.app.remote.api.team.model.request.CreateTeamRequest
import com.futsalgg.app.remote.api.team.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.remote.api.team.TeamApi
import com.futsalgg.app.data.common.error.DataError
import java.io.IOException
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val teamApi: TeamApi
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
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun getTeamLogoPresignedUrl(
        accessToken: String,
        teamId: String
    ): Result<TeamLogoPresignedUrlResponseModel> {
        return try {
            val response = teamApi.getTeamLogoPresignedUrl(
                authHeader = "Bearer $accessToken",
                teamId = teamId
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
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        teamId: String,
        uri: String
    ): Result<TeamLogoPresignedUrlResponseModel> {
        return try {
            val response = teamApi.updateTeamLogo(
                authHeader = "Bearer $accessToken",
                teamId = teamId,
                request = UpdateTeamLogoRequest(uri)
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
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
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
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
                    cause = e
                ) as Throwable
            )
        }
    }
} 