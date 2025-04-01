package com.futsalgg.app.data.repository

import com.futsalgg.app.data.model.request.CreateTeamRequest
import com.futsalgg.app.data.model.request.UpdateTeamLogoRequest
import com.futsalgg.app.data.model.response.TeamLogoPresignedUrlResponse
import com.futsalgg.app.data.remote.api.TeamApi
import com.futsalgg.app.domain.repository.CreateTeamRepository
import javax.inject.Inject

class CreateTeamRepositoryImpl @Inject constructor(
    private val teamApi: TeamApi
) : CreateTeamRepository {

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            val response = teamApi.checkTeamNickname(nickname)
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body.unique)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }

    override suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponse> {
        return try {
            val response = teamApi.getTeamLogoPresignedUrl(
                authHeader = "Bearer $accessToken",
                teamId = teamId
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }

    override suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponse> {
        return try {
            val response = teamApi.updateTeamLogo(
                authHeader = "Bearer $accessToken",
                teamId = teamId,
                request = UpdateTeamLogoRequest(uri)
            )
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(body)
                } ?: Result.failure(Throwable("서버 응답이 비어있습니다."))
            } else {
                Result.failure(Throwable("서버 오류: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
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
                Result.failure(Throwable("팀 생성 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(Throwable("네트워크 오류: ${e.message}"))
        }
    }
} 