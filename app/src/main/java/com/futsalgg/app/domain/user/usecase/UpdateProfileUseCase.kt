package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.User

interface UpdateProfileUseCase {
    suspend operator fun invoke(
        accessToken: String,
        name: String,
        squadNumber: Int? = null
    ): Result<User>
} 