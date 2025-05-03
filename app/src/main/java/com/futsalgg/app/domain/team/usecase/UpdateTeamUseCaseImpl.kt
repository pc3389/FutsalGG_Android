package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class UpdateTeamUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : UpdateTeamUseCase {
    override suspend fun invoke(
        accessToken: String,
        teamId: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit> {
        return teamRepository.updateTeam(
            accessToken = accessToken,
            teamId = teamId,
            name = name,
            introduction = introduction,
            rule = rule,
            matchType = matchType,
            access = access,
            dues = dues
        )
    }
} 