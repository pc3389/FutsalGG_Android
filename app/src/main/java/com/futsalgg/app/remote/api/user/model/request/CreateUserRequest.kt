package com.futsalgg.app.remote.api.user.model.request

/**
 * Request
 *
 * @param nickname 사용자의 닉네임
 * @param birthDate yyyy-MM-dd 포멧
 * @param gender "Male" or "Female"
 * @param agreement 약관 동의 여부
 * @param notification 푸시 알림 켜기 여부
 */
data class CreateUserRequest(
    val nickname: String,
    val birthDate: String,
    val gender: String,
    val agreement: Boolean = true,
    val notification: Boolean
)