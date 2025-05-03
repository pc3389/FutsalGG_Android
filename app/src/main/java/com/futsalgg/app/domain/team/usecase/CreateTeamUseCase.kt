package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.Access
import com.futsalgg.app.domain.common.model.MatchType

interface CreateTeamUseCase {
    suspend fun invoke(
        accessToken: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit>
} 