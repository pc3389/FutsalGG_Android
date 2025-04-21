package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.repository.UserRepository
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CreateUserUseCase {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return userRepository.isNicknameUnique(nickname)
    }

    override suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean,
        notification: Boolean
    ): Result<Unit> {
        return userRepository.createUser(
            accessToken = accessToken,
            nickname = nickname,
            birthDate = birthDate,
            gender = gender,
            agreement = agreement,
            notification = notification
        )
    }
} 