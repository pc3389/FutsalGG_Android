package com.futsalgg.app.presentation.common.state

import com.futsalgg.app.presentation.common.error.UiError

sealed class UiState {
    object Initial : UiState()
    object Loading : UiState()
    object Success : UiState()
    data class Error(val error: UiError) : UiState()
}