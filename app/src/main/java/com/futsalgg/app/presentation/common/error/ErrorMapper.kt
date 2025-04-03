package com.futsalgg.app.presentation.common.error

import com.futsalgg.app.domain.common.error.DomainError

fun DomainError.toUiError(): UiError {
    return when (this) {
        is DomainError.AuthError -> UiError.AuthError(
            message = this.message
        )
        is DomainError.NetworkError -> UiError.NetworkError(
            message = this.message
        )
        is DomainError.ServerError -> UiError.ServerError(
            message = this.message
        )
        is DomainError.ValidationError -> UiError.ValidationError(
            message = this.message
        )
        is DomainError.UnknownError -> UiError.UnknownError(
            message = this.message
        )
    }
}

sealed class UiError {
    data class AuthError(val message: String) : UiError()
    data class NetworkError(val message: String) : UiError()
    data class ServerError(val message: String) : UiError()
    data class ValidationError(val message: String) : UiError()
    data class UnknownError(val message: String) : UiError()
} 