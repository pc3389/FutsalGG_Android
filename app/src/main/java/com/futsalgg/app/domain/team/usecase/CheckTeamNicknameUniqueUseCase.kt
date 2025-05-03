package com.futsalgg.app.domain.team.usecase

interface CheckTeamNicknameUniqueUseCase {
    suspend fun invoke(nickname: String): Result<Boolean>
} 