package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.User

interface GetMyProfileForSettingUseCase {
    suspend operator fun invoke(accessToken: String): Result<User>
} 