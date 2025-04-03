package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.model.MatchType
import com.futsalgg.app.domain.team.model.TeamLogoPresignedUrlResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.domain.common.error.toDomainError
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

    override suspend fun getTeamLogoPresignedUrl(
        accessToken: String,
        teamId: String
    ): Result<TeamLogoPresignedUrlResponseModel> {
        return try {
            createTeamRepository.getTeamLogoPresignedUrl(accessToken, teamId)
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
        }
    }

    override suspend fun updateTeamLogo(
        accessToken: String,
        teamId: String,
        uri: String
    ): Result<TeamLogoPresignedUrlResponseModel> {
        return try {
            createTeamRepository.updateTeamLogo(accessToken, teamId, uri)
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