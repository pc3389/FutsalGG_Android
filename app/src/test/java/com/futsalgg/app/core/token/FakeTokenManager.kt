package com.futsalgg.app.core.token

class FakeTokenManager : ITokenManager {
    private var _accessToken: String? = null
    private var _refreshToken: String? = null

    override fun saveTokens(accessToken: String, refreshToken: String) {
        _accessToken = accessToken
        _refreshToken = refreshToken
    }

    override fun getAccessToken(): String? = _accessToken
    override fun getRefreshToken(): String? = _refreshToken
    override fun isAccessTokenExpired(): Boolean = false
    override fun clearTokens() {
        _accessToken = null
        _refreshToken = null
    }
}