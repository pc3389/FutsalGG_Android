package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.CreateBulkMatchStat

interface CreateMatchStatsBulkUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String,
        stats: List<CreateBulkMatchStat>
    ): Result<Unit>
} 