package com.futsalgg.app.domain.match.usecase

import com.futsalgg.app.domain.match.model.UpdateMatch

interface UpdateMatchUseCase {
    suspend operator fun invoke(
        accessToken: String,
        matchId: String,
        matchDate: String,
        location: String,
        startTime: String? = null,
        endTime: String? = null,
        substituteTeamMemberId: String? = null
    ): Result<UpdateMatch>
} 