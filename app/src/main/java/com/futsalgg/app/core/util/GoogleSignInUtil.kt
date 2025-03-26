package com.futsalgg.app.core.util

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.futsalgg.app.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption


object GoogleSignInUtil {

    /**
     * CredentialManager에서 사용할 Google 로그인 요청 생성
     * @param context context
     */
    fun createGoogleCredentialRequest(context: Context): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(context.getString(R.string.default_web_client_id)) // Firebase에서 제공하는 Web Client ID
            .setFilterByAuthorizedAccounts(false) // 이전 로그인 계정 필터 여부
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}