package com.futsalgg.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.futsalgg.app.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun signInWithGoogleIdToken(
        idToken: String,
        onSuccess: () -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        viewModelScope.launch {
            val result = loginUseCase(idToken)
            result.onSuccess {
                onSuccess()
            }.onFailure {
                onFailure(it)
            }
        }
    }
}