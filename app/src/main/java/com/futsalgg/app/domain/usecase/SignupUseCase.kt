package com.futsalgg.app.domain.usecase

import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.domain.model.Gender
import java.io.File

interface SignupUseCase {
    suspend fun isNicknameUnique(nickname: String): Result<Boolean>
    
    suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean,
        notification: Boolean
    ): Result<Unit>
    
    suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfileResponse>
} 