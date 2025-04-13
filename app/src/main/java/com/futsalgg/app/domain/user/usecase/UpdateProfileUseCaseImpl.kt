package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.User
import com.futsalgg.app.domain.user.repository.UserRepository
import javax.inject.Inject

class UpdateProfileUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UpdateProfileUseCase {
    override suspend operator fun invoke(accessToken: String, name: String, squadNumber: Int?): Result<User> {
        return userRepository.updateProfile(accessToken, name, squadNumber)
    }
} 