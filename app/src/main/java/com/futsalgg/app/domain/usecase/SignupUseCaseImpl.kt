package com.futsalgg.app.domain.usecase

import com.futsalgg.app.data.model.response.UpdateProfileResponse
import com.futsalgg.app.domain.model.Gender
import com.futsalgg.app.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class SignupUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : SignupUseCase {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            userRepository.isNicknameUnique(nickname)
        } catch (e: Exception) {
            Result.failure(Throwable("닉네임 중복 체크 중 오류가 발생했습니다: ${e.message}"))
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
            Result.failure(Throwable("회원가입 중 오류가 발생했습니다: ${e.message}"))
        }
    }

    override suspend fun uploadProfileImage(
        accessToken: String,
        file: File
    ): Result<UpdateProfileResponse> {
        return try {
            userRepository.uploadProfileImage(accessToken, file)
        } catch (e: Exception) {
            Result.failure(Throwable("프로필 이미지 업로드 중 오류가 발생했습니다: ${e.message}"))
        }
    }
} 