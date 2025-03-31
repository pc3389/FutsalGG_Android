package com.futsalgg.app.domain.repository

interface UserRepository {
    suspend fun isNicknameUnique(nickname: String): Result<Boolean>
}