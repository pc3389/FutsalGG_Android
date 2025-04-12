package com.futsalgg.app.domain.user.usecase

interface UpdateNotificationUseCase {
    suspend operator fun invoke(accessToken: String, notification: Boolean): Result<Unit>
} 