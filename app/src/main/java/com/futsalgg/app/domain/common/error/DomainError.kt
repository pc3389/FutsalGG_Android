package com.futsalgg.app.domain.common.error

sealed class DomainError : Throwable() {
    data class NetworkError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DomainError()

    data class ServerError(
        val code: Int,
        override val message: String,
        override val cause: Throwable? = null
    ) : DomainError()

    data class AuthError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DomainError()

    data class ValidationError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DomainError()

    data class UnknownError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DomainError()
} 