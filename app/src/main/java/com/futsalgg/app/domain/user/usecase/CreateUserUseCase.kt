package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.Gender

interface CreateUserUseCase {
    suspend fun isNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean,
        notification: Boolean
    ): Result<Unit>
} 