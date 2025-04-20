package com.futsalgg.app.domain.auth.usecase

import com.futsalgg.app.domain.auth.repository.ITokenManager
import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.domain.common.error.toDomainError
import javax.inject.Inject

class AuthUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenManager: ITokenManager
) : AuthUseCase {

    override suspend operator fun invoke(idToken: String): Result<LoginResponseModel> {
        return try {
            // 1. Firebase 인증
            val firebaseResult = authRepository.signInWithGoogleIdToken(idToken)
            if (firebaseResult.isFailure) {
                val error = firebaseResult.exceptionOrNull()
                return Result.failure(error?.toDomainError() ?: DomainError.UnknownError("firebase 인증 알 수 없는 오류가 발생했습니다."))
            }


            // 2. 서버 로그인
            val serverResult = authRepository.loginWithGoogleToken()
            if (serverResult.isFailure) {
                val error = serverResult.exceptionOrNull()
                return Result.failure(error?.toDomainError() ?: DomainError.UnknownError("서버로그인 도메인 알 수 없는 오류가 발생했습니다."))
            }

            // 3. 토큰 저장
            serverResult.getOrNull()?.let { response ->
                tokenManager.saveTokens(response.accessToken, response.refreshToken)
            }

            serverResult
        } catch (e: Exception) {
            Result.failure(e.toDomainError())
        }
    }
} 