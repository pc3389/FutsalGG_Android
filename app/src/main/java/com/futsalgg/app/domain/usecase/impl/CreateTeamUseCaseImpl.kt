package com.futsalgg.app.domain.usecase.impl

import com.futsalgg.app.data.model.response.TeamLogoPresignedUrlResponse
import com.futsalgg.app.domain.model.team.Access
import com.futsalgg.app.domain.model.team.MatchType
import com.futsalgg.app.domain.repository.CreateTeamRepository
import com.futsalgg.app.domain.usecase.CreateTeamUseCase
import javax.inject.Inject

class CreateTeamUseCaseImpl @Inject constructor(
    private val createTeamRepository: CreateTeamRepository
) : CreateTeamUseCase {

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            createTeamRepository.isTeamNicknameUnique(nickname)
        } catch (e: Exception) {
            Result.failure(Throwable("팀 닉네임 중복 확인 중 오류가 발생했습니다: ${e.message}"))
        }
    }

    override suspend fun getTeamLogoPresignedUrl(accessToken: String, teamId: String): Result<TeamLogoPresignedUrlResponse> {
        return try {
            createTeamRepository.getTeamLogoPresignedUrl(accessToken, teamId)
        } catch (e: Exception) {
            Result.failure(Throwable("팀 로고 presigned URL 획득 중 오류가 발생했습니다: ${e.message}"))
        }
    }

    override suspend fun updateTeamLogo(accessToken: String, teamId: String, uri: String): Result<TeamLogoPresignedUrlResponse> {
        return try {
            createTeamRepository.updateTeamLogo(accessToken, teamId, uri)
        } catch (e: Exception) {
            Result.failure(Throwable("팀 로고 업데이트 중 오류가 발생했습니다: ${e.message}"))
        }
    }

    override suspend fun createTeam(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit> {
        return try {
            createTeamRepository.createTeam(
                accessToken = accessToken,
                name = name,
                introduction = introduction,
                rule = rule,
                matchType = matchType.name,
                access = access.name,
                dues = dues
            )
        } catch (e: Exception) {
            Result.failure(Throwable("팀 생성 중 오류가 발생했습니다: ${e.message}"))
        }
    }
} 