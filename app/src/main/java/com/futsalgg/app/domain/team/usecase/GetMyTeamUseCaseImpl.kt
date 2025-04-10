package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.MyTeam
import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class GetMyTeamUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : GetMyTeamUseCase {
    override suspend fun invoke(accessToken: String): Result<MyTeam> =
        teamRepository.getMyTeam(accessToken)
}