package com.futsalgg.app.domain.auth.usecase

import com.futsalgg.app.domain.auth.model.LoginResponseModel

interface AuthUseCase {
    suspend operator fun invoke(idToken: String): Result<LoginResponseModel>
} 