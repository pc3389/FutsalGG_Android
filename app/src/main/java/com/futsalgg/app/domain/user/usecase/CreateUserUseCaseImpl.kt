package com.futsalgg.app.domain.user.usecase

import com.futsalgg.app.domain.user.model.Gender
import com.futsalgg.app.domain.user.model.UpdateProfilePhotoResponseModel
import com.futsalgg.app.domain.user.repository.UserRepository
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.common.error.toDomainError
import java.io.File
import javax.inject.Inject

class CreateUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : CreateUserUseCase {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            userRepository.isNicknameUnique(nickname)
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
        }
    }

    override suspend fun createUser(
        accessToken: String,
        nickname: String,
        birthDate: String,
        gender: Gender,
        agreement: Boolean,
        notification: Boolean
    ): Result<Unit> {
        return try {
            userRepository.createUser(
                accessToken = accessToken,
                nickname = nickname,
                birthDate = birthDate,
                gender = gender,
                agreement = agreement,
                notification = notification
            )
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
        }
    }

    override suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfilePhotoResponseModel> {
        return try {
            userRepository.uploadProfileImage(accessToken, file)
        } catch (e: Exception) {
            Result.failure(
                DomainError.ValidationError(
                    message = "프로필 이미지 업로드 중 오류가 발생했습니다.",
                    cause = e
                )
            )
        }
    }
} 