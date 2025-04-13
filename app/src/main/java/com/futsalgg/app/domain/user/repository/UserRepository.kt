package com.futsalgg.app.domain.user.repository

import com.futsalgg.app.domain.user.model.ProfilePresignedUrlResponseModel
import com.futsalgg.app.domain.user.model.UpdateProfilePhotoResponseModel
import com.futsalgg.app.domain.user.model.Gender
import java.io.File
import com.futsalgg.app.domain.user.model.User

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

    suspend fun updateProfilePhoto(accessToken: String, uri: String): Result<UpdateProfilePhotoResponseModel>

    suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfilePhotoResponseModel>

    suspend fun getMyProfile(accessToken: String): Result<User>

    suspend fun updateNotification(accessToken: String, notification: Boolean): Result<Unit>

    suspend fun updateProfile(accessToken: String, name: String, squadNumber: Int? = null): Result<User>
} 