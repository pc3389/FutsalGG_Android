package com.futsalgg.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.core.token.ITokenManager
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val googleLoginRepository: GoogleLoginRepository,
    private val tokenManager: ITokenManager
) : ViewModel() {

    fun signInWithGoogleIdToken(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        viewModelScope.launch {
            authenticateWithFirebase(
                idToken,
                onSuccess = {
                    viewModelScope.launch {
                        loginToServer(idToken, onSuccess, onFailure)
                    }
                },
                onFailure = onFailure
            )
        }
    }

    private suspend fun authenticateWithFirebase(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        val result = googleLoginRepository.signInWithGoogleIdToken(idToken)
        result.onSuccess {
            onSuccess()
        }.onFailure {
            onFailure(it)
        }
    }

    private suspend fun loginToServer(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        val result = loginRepository.loginWithGoogleToken(idToken)
        result.onSuccess { response ->
            tokenManager.saveTokens(response.accessToken, response.refreshToken)
            onSuccess()
        }.onFailure { onFailure(it) }
    }

}