package com.futsalgg.app.presentation.common.error

import com.futsalgg.app.domain.common.error.DomainError

fun DomainError.toUiError(): UiError {
    return when (this) {
        is DomainError.AuthError -> UiError.AuthError(
            message = this.message,
            cause = this.cause
        )
        is DomainError.NetworkError -> UiError.NetworkError(
            message = this.message,
            cause = this.cause
        )
        is DomainError.ServerError -> UiError.ServerError(
            message = this.message,
            code = this.code,
            cause = this.cause
        )
        is DomainError.ValidationError -> UiError.ValidationError(
            message = this.message,
            cause = this.cause
        )
        is DomainError.UnknownError -> UiError.UnknownError(
            message = this.message,
            cause = this.cause
        )
    }
}

sealed class UiError {
    data class AuthError(val message: String, val cause: Throwable? = null) : UiError()
    data class NetworkError(val message: String, val cause: Throwable? = null) : UiError()
    data class ServerError(val message: String, val code: Int, val cause: Throwable? = null) : UiError()
    data class ValidationError(val message: String, val cause: Throwable? = null) : UiError()
    data class UnknownError(val message: String, val cause: Throwable? = null) : UiError()
}

fun UiError.getMessage(): String = when (this) {
    is UiError.AuthError -> message
    is UiError.NetworkError -> message
    is UiError.ServerError -> message
    is UiError.ValidationError -> message
    is UiError.UnknownError -> message
}

fun UiError.getCode(): String = when (this) {
    is UiError.ServerError -> code.toString()
    else -> ""
}

fun UiError.getType(): String = when (this) {
    is UiError.AuthError -> "AuthError"
    is UiError.NetworkError -> "NetworkError"
    is UiError.ServerError -> "ServerError"
    is UiError.ValidationError -> "ValidationError"
    is UiError.UnknownError -> "UnknownError"
}

fun UiError.getCause(): Throwable? = when (this) {
    is UiError.AuthError -> cause
    is UiError.NetworkError -> cause
    is UiError.ServerError -> cause
    is UiError.ValidationError -> cause
    is UiError.UnknownError -> cause
}