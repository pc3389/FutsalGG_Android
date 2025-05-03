package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class CreateTeamUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : CreateTeamUseCase {

    override suspend fun invoke(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit> {
        return teamRepository.createTeam(
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