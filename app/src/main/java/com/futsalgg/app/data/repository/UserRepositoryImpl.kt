package com.futsalgg.app.data.repository

import com.futsalgg.app.data.remote.api.UserApi
import com.futsalgg.app.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: UserApi
) : UserRepository {

    override suspend fun isNicknameUnique(nickname: String): Result<Boolean> {
        return try {
            val response = api.checkNickname(nickname)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) Result.success(body.unique)
                else Result.failure(Throwable("Empty response"))
            } else {
                Result.failure(Throwable("API error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}