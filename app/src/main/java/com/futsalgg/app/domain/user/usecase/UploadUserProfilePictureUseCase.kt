package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.UpdateProfilePhotoResponseModel
import java.io.File

interface UploadUserProfilePictureUseCase {
    suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfilePhotoResponseModel>
}