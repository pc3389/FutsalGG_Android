package com.futsalgg.app.domain.common.error

import com.futsalgg.app.data.common.error.DataError

fun Throwable.toDomainError(): DomainError {
    return when (this) {
        is DataError.AuthError -> DomainError.AuthError(
            message = this.message,
            cause = this.cause
        )
        is DataError.NetworkError -> DomainError.NetworkError(
            message = this.message,
            cause = this.cause
        )
        is DataError.ServerError -> DomainError.ServerError(
            code = 0,
            message = this.message,
            cause = this.cause
        )
        is DataError.UnknownError -> DomainError.UnknownError(
            message = this.message,
            cause = this.cause
        )
        else -> DomainError.UnknownError(
            message = "알 수 없는 오류가 발생했습니다.",
            cause = this
        )
    }
} 