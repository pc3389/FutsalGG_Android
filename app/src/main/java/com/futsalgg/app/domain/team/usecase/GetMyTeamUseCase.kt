package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.MyTeam

interface GetMyTeamUseCase {
    suspend operator fun invoke(accessToken: String): Result<MyTeam>
}