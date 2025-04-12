package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateNotificationUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateNotificationUseCase {
    override suspend operator fun invoke(accessToken: String, notification: Boolean): Result<Unit> {
        return userRepository.updateNotification(accessToken, notification)
    }
} 