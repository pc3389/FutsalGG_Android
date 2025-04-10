package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.SearchTeamResponseModel
import com.futsalgg.app.domain.team.repository.TeamRepository
import javax.inject.Inject

class SearchTeamsUseCaseImpl @Inject constructor(
    private val teamRepository: TeamRepository
) : SearchTeamsUseCase {
    override suspend fun invoke(name: String): Result<SearchTeamResponseModel> {
        return teamRepository.searchTeams(name)
    }
}