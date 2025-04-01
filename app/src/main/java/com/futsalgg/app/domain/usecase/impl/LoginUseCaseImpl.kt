package com.futsalgg.app.domain.usecase.impl

import com.futsalgg.app.core.token.ITokenManager
import com.futsalgg.app.data.model.response.LoginResponse
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import com.futsalgg.app.domain.repository.UserRepository
import com.futsalgg.app.domain.usecase.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val loginRepository: LoginRepository,
    private val googleLoginRepository: GoogleLoginRepository,
    private val tokenManager: ITokenManager
) : LoginUseCase {

    override suspend operator fun invoke(idToken: String): Result<LoginResponse> {
        return try {
            // 1. Firebase 인증
            val firebaseResult = googleLoginRepository.signInWithGoogleIdToken(idToken)
            if (firebaseResult.isFailure) {
                return Result.failure(firebaseResult.exceptionOrNull()!!)
            }

            // 2. 서버 로그인
            val serverResult = loginRepository.loginWithGoogleToken(idToken)
            if (serverResult.isFailure) {
                return Result.failure(serverResult.exceptionOrNull()!!)
            }

            // 3. 토큰 저장
            serverResult.getOrNull()?.let { response ->
                tokenManager.saveTokens(response.accessToken, response.refreshToken)
            }

            serverResult
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 