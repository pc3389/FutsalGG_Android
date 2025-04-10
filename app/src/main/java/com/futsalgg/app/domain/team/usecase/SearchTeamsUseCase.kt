package com.futsalgg.app.domain.team.usecase

import com.futsalgg.app.domain.team.model.SearchTeamResponseModel

interface SearchTeamsUseCase {
    suspend operator fun invoke(name: String): Result<SearchTeamResponseModel>
}