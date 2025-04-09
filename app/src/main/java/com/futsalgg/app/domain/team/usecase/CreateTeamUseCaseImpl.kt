package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.common.error.toDomainError
import com.futsalgg.app.domain.team.model.TeamLogoResponseModel
import java.io.File
import javax.inject.Inject

class CreateTeamUseCaseImpl @Inject constructor(
    private val createTeamRepository: TeamRepository
) : CreateTeamUseCase {

    override suspend fun isTeamNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            createTeamRepository.isTeamNicknameUnique(nickname)
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
        }
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        file: File
    ): Result<TeamLogoResponseModel> {
        return try {
            createTeamRepository.uploadLogoImage(accessToken, file)
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
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
            Result.failure(e.toDomainError())
        }
    }
} 