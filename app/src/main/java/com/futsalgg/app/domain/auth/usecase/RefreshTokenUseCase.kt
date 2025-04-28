package com.futsalgg.app.domain.auth.usecase

import com.futsalgg.app.domain.auth.model.LoginResponseModel

interface RefreshTokenUseCase {
    suspend operator fun invoke(refreshToken: String): Result<LoginResponseModel>
} 