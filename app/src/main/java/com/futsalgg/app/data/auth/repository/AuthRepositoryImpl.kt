package com.futsalgg.app.data.auth.repository

import android.util.Log
import com.futsalgg.app.data.common.error.DataError
import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.model.Platform
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.remote.api.auth.AuthApi
import com.futsalgg.app.remote.api.auth.model.request.LoginRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume


class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    override suspend fun loginWithGoogleToken(
        platform: Platform
    ): Result<LoginResponseModel> {
        return try {
            // 1. Firebase 토큰 가져오기
            val firebaseToken = auth.currentUser?.getIdToken(true)?.await()?.token
                ?: return Result.failure(
                    DataError.ServerError(
                        message = "서버 로그인 실패: Firebase 토큰을 가져올 수 없음",
                        cause = null
                    )
                )

            // 2. 로그인 요청 생성 및 전송
            val request = LoginRequest(token = firebaseToken, platform = platform.name)
            val response = authApi.login(request)

            // 3. 응답 처리
            when {
                !response.isSuccessful -> Result.failure(
                    DataError.ServerError(
                        message = "서버 로그인 실패: ${response.code()}",
                        cause = null
                    )
                )
                response.body() == null -> Result.failure(
                    DataError.ServerError(
                        message = "서버 로그인 실패: 응답 데이터가 없음",
                        cause = null
                    )
                )
                else -> {
                    val body = response.body()!!
                    Result.success(
                        LoginResponseModel(
                            accessToken = body.data.accessToken,
                            refreshToken = body.data.refreshToken,
                            isNew = body.data.isNew
                        )
                    )
                }
            }
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "데이터 레이어 서버 로그인 중 오류 발생",
                    cause = e
                )
            )
        }
    }

    override suspend fun signInWithGoogleIdToken(idToken: String): Result<Unit> {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        return suspendCancellableCoroutine { continuation ->
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(
                            Result.failure(
                                DataError.AuthError(
                                    message = task.exception?.message ?: "Google 로그인 실패",
                                    cause = task.exception
                                ) as Throwable
                            )
                        )
                    }
                }
        }
    }

    override suspend fun getFirebaseToken(): Result<String> {
        return try {
            val task = auth.currentUser?.getIdToken(true)
            if (task != null) {
                val token = task.await()
                if (!token.token.isNullOrEmpty()) {
                    Result.success(token.token!!)
                } else Result.failure(
                    DataError.ServerError(
                        message = "서버 로그인 실패: 토큰 없존재하지 않음}",
                        cause = null
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DataError.ServerError(
                        message = "서버 로그인 실패: getIdToken 실패}",
                        cause = null
                    ) as Throwable
                )
            }
        } catch (e: Exception) {
            Result.failure(
                DataError.UnknownError(
                    message = "서버 로그인 알 수 없는 오류가 발생했습니다.",
                    cause = e
                ) as Throwable
            )
        }
    }

}