package com.futsalgg.app.data.teammember.repository

import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.data.teammember.mapper.toDomain
import com.futsalgg.app.domain.teammember.model.TeamMember
import com.futsalgg.app.domain.teammember.repository.TeamMemberRepository
import com.futsalgg.app.remote.api.teammember.TeamMemberApi
import java.io.IOException
import javax.inject.Inject

class TeamMemberRepositoryImpl @Inject constructor(
    private val teamMemberApi: TeamMemberApi
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
} 