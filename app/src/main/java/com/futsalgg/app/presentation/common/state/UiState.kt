package com.futsalgg.app.presentation.common.state

import com.futsalgg.app.presentation.common.error.UiError

sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
    data object Success : UiState()
    data class Error(val error: UiError) : UiState()
}