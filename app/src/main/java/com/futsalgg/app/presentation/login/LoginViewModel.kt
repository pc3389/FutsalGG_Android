package com.futsalgg.app.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.data.model.Platform
import com.futsalgg.app.data.model.response.LoginResponse
import com.futsalgg.app.domain.repository.GoogleLoginRepository
import com.futsalgg.app.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val googleLoginRepository: GoogleLoginRepository
) : ViewModel() {

    fun signInWithGoogleIdToken(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        viewModelScope.launch {
            val result = googleLoginRepository.signInWithGoogleIdToken(idToken)
            result
                .onSuccess {
                    val serverResult = loginRepository.loginWithGoogleToken(idToken)
                    serverResult
                        .onSuccess { loginResponse ->
                            // TODO: accessToken, refreshToken 저장
                            onSuccess()
                        }
                        .onFailure { serverError ->
                            onFailure(serverError)
                        }
                }
                .onFailure { error -> onFailure(error) }
        }
    }
}