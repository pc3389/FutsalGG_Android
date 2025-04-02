package com.futsalgg.app.domain.auth.repository

interface ITokenManager {
    fun saveTokens(accessToken: String, refreshToken: String)
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun isAccessTokenExpired(): Boolean
    fun clearTokens()
} 