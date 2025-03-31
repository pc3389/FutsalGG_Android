package com.futsalgg.app.domain.repository

import com.futsalgg.app.data.model.response.ProfilePresignedUrlResponse
import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.domain.model.Gender
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

    suspend fun getProfilePresignedUrl(accessToken: String): Result<ProfilePresignedUrlResponse>

    suspend fun updateProfile(accessToken: String, uri: String): Result<UpdateProfileResponse>

    suspend fun uploadProfileImage(accessToken: String, file: File): Result<UpdateProfileResponse>
}