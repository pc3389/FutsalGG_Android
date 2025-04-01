package com.futsalgg.app.domain.usecase

import com.futsalgg.app.data.model.response.LoginResponse

interface LoginUseCase {
    suspend operator fun invoke(idToken: String): Result<LoginResponse>
} 