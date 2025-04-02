package com.futsalgg.app.domain.auth.usecase

import com.futsalgg.app.core.token.ITokenManager
import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.common.error.DomainError
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
                return Result.failure(
                    DomainError.AuthError(
                        message = "Firebase 인증 실패",
                        cause = firebaseResult.exceptionOrNull()
                    )
                )
            }

            // 2. 서버 로그인
            val serverResult = authRepository.loginWithGoogleToken(idToken)
            if (serverResult.isFailure) {
                return Result.failure(
                    DomainError.AuthError(
                        message = "서버 로그인 실패",
                        cause = serverResult.exceptionOrNull()
                    )
                )
            }

            // 3. 토큰 저장
            serverResult.getOrNull()?.let { response ->
                tokenManager.saveTokens(response.accessToken, response.refreshToken)
            }

            serverResult
        } catch (e: Exception) {
            Result.failure(
                DomainError.UnknownError(
                    message = "알 수 없는 오류가 발생했습니다.",
                    cause = e
                )
            )
        }
    }
} 