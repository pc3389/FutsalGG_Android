package com.futsalgg.app.domain.team.usecase

interface RejectTeamMemberUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit>
} 