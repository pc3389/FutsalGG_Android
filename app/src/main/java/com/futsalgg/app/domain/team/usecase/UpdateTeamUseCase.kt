package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.common.model.MatchType
import com.futsalgg.app.domain.team.model.Access

interface UpdateTeamUseCase {
    suspend fun invoke(
        accessToken: String,
        teamId: String,
        name: String,
        introduction: String,
        rule: String,
        matchType: MatchType,
        access: Access,
        dues: Int
    ): Result<Unit>
} 