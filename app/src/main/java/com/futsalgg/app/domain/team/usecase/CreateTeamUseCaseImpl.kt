package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import java.io.File
import javax.inject.Inject

class CreateTeamUseCaseImpl @Inject constructor(
    private val createTeamRepository: TeamRepository
) : CreateTeamUseCase {

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return createTeamRepository.isTeamNicknameUnique(nickname)
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel> {
        return createTeamRepository.uploadLogoImage(accessToken, file)
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
        return createTeamRepository.createTeam(
            accessToken = accessToken,
            name = name,
            introduction = introduction,
            rule = rule,
            matchType = matchType.name,
            access = access.name,
            dues = dues
        )
    }
} 