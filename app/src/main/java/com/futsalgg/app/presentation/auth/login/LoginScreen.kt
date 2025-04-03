package com.futsalgg.app.presentation.auth.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.futsalgg.app.R
import com.futsalgg.app.core.util.GoogleSignInUtil
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onClick: () -> Unit,
    credentialManager: CredentialManager,
    onLoginSuccess: () -> Unit,
    context: Context
) {
    val coroutineScope = rememberCoroutineScope()

    Contents(onClick = {
        // Credential Manager 요청 생성
        coroutineScope.launch {
            try {
                // 1. 요청 생성
                val request = GoogleSignInUtil.createGoogleCredentialRequest(context)

                // 2. Credential 요청 (suspend)
                val credential = credentialManager.getCredential(context, request).credential

                // 3. idToken 추출 및 ViewModel에 전달
                handleCredential(credential, viewModel, onLoginSuccess)
            } catch (e: Exception) {
                Log.e("LoginScreen", "Google Sign-In failed", e)
            }
        }

        onClick()
    })
}

@Composable
fun Contents(onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_background),
            contentDescription = "",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.futsalgg_logo),
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 68.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.temp_login_button),
                contentDescription = "",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() }
            )
        }
    }
}

private fun handleCredential(
    credential: Credential,
    viewModel: LoginViewModel,
    onSuccess: () -> Unit
) {
    if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
        val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
        val idToken = googleIdTokenCredential.idToken

        viewModel.signInWithGoogleIdToken(
            idToken = idToken,
            onSuccess = onSuccess,
            onFailure = { e ->
                Log.e("LoginScreen", "Login failed", e)
            }
        )
    } else {
        Log.w("LoginScreen", "Unsupported credential type")
    }
}

@Preview
@Composable
fun ContentPreview() {
    Contents() {}
}