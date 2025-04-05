package com.futsalgg.app.presentation.team.createteam

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import com.futsalgg.app.domain.team.usecase.CreateTeamUseCase
import java.io.File

class FakeCreateTeamUseCase : CreateTeamUseCase {
    var shouldSucceed = true
    var isUnique = true

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return if (shouldSucceed) {
            Result.success(isUnique)
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel> {
        return if (shouldSucceed) {
            Result.success(TeamLogoResponseModel("testUrl", "testUri"))
        } else {
            Result.failure(Exception("테스트 에러"))
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
        return if (shouldSucceed) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("테스트 에러"))
        }
    }
} 