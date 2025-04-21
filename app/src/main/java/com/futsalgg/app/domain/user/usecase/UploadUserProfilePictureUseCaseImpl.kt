package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.UpdateProfilePhotoResponseModel
import com.futsalgg.app.domain.user.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UploadUserProfilePictureUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : UploadUserProfilePictureUseCase {
    override suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfilePhotoResponseModel> {
        return userRepository.uploadProfileImage(accessToken, file)
    }
}