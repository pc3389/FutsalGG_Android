package com.futsalgg.app.data.common.error

sealed class DataError : Throwable() {
    data class AuthError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DataError() {
        override fun toString(): String = message
    }

    data class NetworkError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DataError() {
        override fun toString(): String = message
    }

    data class ServerError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DataError() {
        override fun toString(): String = message
    }

    data class UnknownError(
        override val message: String,
        override val cause: Throwable? = null
    ) : DataError() {
        override fun toString(): String = message
    }
} 