package com.futsalgg.app.data.auth.repository

import android.util.Log
import com.futsalgg.app.remote.api.auth.model.request.LoginRequest
import com.futsalgg.app.domain.auth.model.Platform
import com.futsalgg.app.domain.auth.model.LoginResponseModel
import com.futsalgg.app.domain.auth.repository.AuthRepository
import com.futsalgg.app.remote.api.auth.AuthApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import com.futsalgg.app.data.common.error.DataError
import java.io.IOException

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    override suspend fun loginWithGoogleToken(
        token: String,
        platform: Platform
    ): Result<LoginResponseModel> {
        return try {
            val request = LoginRequest(token = token, platform = platform.name)
            Log.d("Login Token", token)
            Log.d("Login", Platform.GOOGLE.name)
            val response = authApi.login(request)

            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                Result.success(
                    LoginResponseModel(
                        accessToken = body.accessToken,
                        refreshToken = body.refreshToken,
                        isNew = body.isNew,
                    )
                )
            } else {
                Result.failure(DataError.ServerError(
                    message = "서버 로그인 실패: ${response.code()}",
                    cause = null
                ) as Throwable)
            }
        } catch (e: IOException) {
            Result.failure(DataError.NetworkError(
                message = "네트워크 연결을 확인해주세요.",
                cause = e
            ) as Throwable)
        } catch (e: Exception) {
            Result.failure(DataError.UnknownError(
                message = "알 수 없는 오류가 발생했습니다.",
                cause = e
            ) as Throwable)
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
                        continuation.resume(Result.failure(DataError.AuthError(
                            message = task.exception?.message ?: "Google 로그인 실패",
                            cause = task.exception
                        ) as Throwable))
                    }
                }
        }
    }
} 