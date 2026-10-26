package com.example.cineswipe.shared.core
sealed interface AppError {

    /** Нет интернета */
    data object NoNetwork : AppError

    /** Сервер отклонил токен (HTTP 401). */
    data object Unauthorized : AppError

    /** Ресурс не найден (HTTP 404). */
    data object NotFound : AppError

    /** Слишком много запросов (HTTP 429). */
    data class RateLimited(val retryAfterSec: Int?) : AppError

    /** Ошибка на стороне сервера (HTTP 5xx), code — код ответа. */
    data class Server(val code: Int) : AppError

    /** Ответ сервера не удалось разобрать. */
    data object Parse : AppError

    /** Любая неожиданная ошибка; cause сохраняется для логов. */
    data class Unknown(val cause: Throwable?) : AppError
}