package com.futsalgg.app.domain.auth.usecase

import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.common.error.DomainError
import javax.inject.Inject

class RefreshTokenUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : RefreshTokenUseCase {

    override suspend fun invoke(refreshToken: String): Result<LoginResponseModel> {
        return try {
            val result = authRepository.refreshToken(refreshToken)
            if (result.isFailure) {
                val error = result.exceptionOrNull()
                return Result.failure(
                    error ?: DomainError.UnknownError("토큰 갱신 중 알 수 없는 오류가 발생했습니다.")
                )
            }
            result
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 