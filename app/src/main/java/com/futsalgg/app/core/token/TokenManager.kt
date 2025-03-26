package com.futsalgg.app.core.token

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_tokens",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveTokens(accessToken: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .putLong(KEY_ACCESS_TOKEN_TIMESTAMP, System.currentTimeMillis())
            .apply()
    }

    fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    fun getRefreshToken(): String? = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    fun isAccessTokenExpired(): Boolean {
        val savedAt = sharedPreferences.getLong(KEY_ACCESS_TOKEN_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()
        return now - savedAt > ACCESS_TOKEN_EXPIRATION
    }

    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ACCESS_TOKEN_TIMESTAMP = "access_token_saved_at"

        private const val ACCESS_TOKEN_EXPIRATION = 7 * 24 * 60 * 60 * 1000L // 7일
    }
}