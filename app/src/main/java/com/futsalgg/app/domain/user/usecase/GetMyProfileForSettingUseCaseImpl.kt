package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.User
import com.futsalgg.app.domain.user.repository.UserRepository
import javax.inject.Inject

class GetMyProfileForSettingUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetMyProfileForSettingUseCase {
    override suspend operator fun invoke(accessToken: String): Result<User> {
        return userRepository.getMyProfile(accessToken)
    }
} 