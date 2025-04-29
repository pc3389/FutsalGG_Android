package com.futsalgg.app.domain.team.usecase

interface AcceptTeamMemberUseCase {
    suspend operator fun invoke(
        accessToken: String,
        teamMemberId: String
    ): Result<Unit>
} 