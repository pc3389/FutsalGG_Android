package com.futsalgg.app.core.token

interface ITokenManager {
    fun saveTokens(accessToken: String, refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun isAccessTokenExpired(): Boolean
    fun clearTokens()
}