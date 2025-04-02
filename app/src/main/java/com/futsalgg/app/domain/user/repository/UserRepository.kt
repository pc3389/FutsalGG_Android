package com.futsalgg.app.domain.user.repository

import com.futsalgg.app.domain.user.model.ProfilePresignedUrlResponseModel
import com.futsalgg.app.domain.user.model.UpdateProfileResponseModel
import com.futsalgg.app.domain.user.model.Gender
import java.io.File

interface UserRepository {
    suspend fun isNicknameUnique(nickname: String): Result<Boolean>

    suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean = true,
        notification: Boolean
    ): Result<Unit>

    suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponseModel>

    suspend fun updateProfile(accessToken: String, uri: String): Result<UpdateProfileResponseModel>

    suspend fun uploadProfileImage(accessToken: String, file: File): Result<UpdateProfileResponseModel>
} 