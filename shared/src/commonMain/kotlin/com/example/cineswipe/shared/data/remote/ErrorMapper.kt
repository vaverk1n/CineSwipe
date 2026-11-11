package com.example.cineswipe.shared.data.remote

import com.example.cineswipe.shared.core.AppError
import com.example.cineswipe.shared.core.AppException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpHeaders
import io.ktor.serialization.ContentConvertException
import kotlinx.io.IOException // В Ktor 3 используется kotlinx.io
import kotlinx.serialization.SerializationException

/** Превращает любое исключение сетевого слоя в [AppException]. */
fun Throwable.toAppException(): AppException = when (this) {
    is AppException -> this
    is ResponseException -> AppException(toAppError(this), this)
    is SerializationException, is ContentConvertException -> AppException(AppError.Parse, this)
    is HttpRequestTimeoutException, is ConnectTimeoutException, is SocketTimeoutException, is IOException -> AppException(AppError.NoNetwork, this)
    else -> AppException(AppError.Unknown(this), this)
}

private fun toAppError(exception: ResponseException): AppError {
    val code = exception.response.status.value
    return when (code) {
        401 -> AppError.Unauthorized
        404 -> AppError.NotFound
        429 -> AppError.RateLimited(
            retryAfterSec = exception.response.headers[HttpHeaders.RetryAfter]?.trim()?.toIntOrNull()
        )
        in 500..599 -> AppError.Server(code)
        else -> AppError.Unknown(exception)
    }
}