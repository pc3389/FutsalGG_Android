package com.futsalgg.app.data.auth.repository

import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.model.Platform
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.domain.common.error.DomainError
import com.futsalgg.app.remote.api.auth.AuthApi
import com.futsalgg.app.remote.api.auth.model.request.LoginRequest
import com.futsalgg.app.remote.api.common.ApiResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.io.IOException
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
                    DomainError.AuthError(
                        message = "서버 로그인 실패: Firebase 토큰을 가져올 수 없음",
                        cause = null
                    )
                )

            // 2. 로그인 요청 생성 및 전송
            val request = LoginRequest(token = firebaseToken, platform = platform.name)
            val response = authApi.login(request)

            // 3. 응답 처리
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        LoginResponseModel(
                            accessToken = body.data.accessToken,
                            refreshToken = body.data.refreshToken,
                            isNew = body.data.isNew
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java)

                Result.failure(
                    DomainError.ServerError(
                        message = "서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
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
                                DomainError.AuthError(
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
                    DomainError.UnknownError(
                        message = "getFirebaseToken: 토큰 존재하지 않음",
                        cause = task.exception
                    ) as Throwable
                )
            } else {
                Result.failure(
                    DomainError.UnknownError(
                        message = "getFirebaseToken: getIdToken task null",
                    ) as Throwable
                )
            }
        } catch (e: Exception) {
            Result.failure(
                DomainError.UnknownError(
                    message = "getFirebaseToken 알 수 없는 오류가 발생했습니다: ${e.message}",
                    cause = e
                ) as Throwable
            )
        }
    }

    override suspend fun refreshToken(refreshToken: String): Result<LoginResponseModel> {
        return try {
            val response = authApi.refreshToken(
                refreshToken = "Bearer $refreshToken"
            )
            
            if (response.isSuccessful) {
                response.body()?.let { body ->
                    Result.success(
                        LoginResponseModel(
                            accessToken = body.data.accessToken,
                            refreshToken = body.data.refreshToken,
                            isNew = body.data.isNew
                        )
                    )
                } ?: Result.failure(
                    DomainError.ServerError(
                        message = "서버 응답이 비어있습니다.",
                        code = response.code()
                    ) as Throwable
                )
            } else {
                val errorBody = response.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ApiResponse::class.java as Class<ApiResponse<*>>)

                Result.failure(
                    DomainError.ServerError(
                        message = "서버 오류: ${errorResponse.message}",
                        code = response.code()
                    ) as Throwable
                )
            }
        } catch (e: IOException) {
            Result.failure(
                DomainError.NetworkError(
                    message = "네트워크 연결을 확인해주세요.",
                    cause = e
                ) as Throwable
            )
        }
    }

}