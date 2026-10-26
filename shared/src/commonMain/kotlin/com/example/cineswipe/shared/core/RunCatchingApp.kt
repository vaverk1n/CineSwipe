package com.example.cineswipe.shared.core

import kotlin.coroutines.cancellation.CancellationException

/**
 * Выполняет [block] и превращает исключение в [AppResult.Failure].
 *
 * [CancellationException] пробрасывается дальше: иначе отмена корутины
 * (например, при уходе с экрана) была бы «проглочена» и корутина продолжила бы работу.
 */
inline fun <T> runCatchingApp(block: () -> T): AppResult<T> =
    try {
        AppResult.Success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        AppResult.Failure(e.toAppError())
    }

/**
 * Базовое преобразование исключения в [AppError].
 * Исключения Ktor и сериализации распознаются позже, в ErrorMapper на этапе 2.
 */
fun Throwable.toAppError(): AppError = AppError.Unknown(this)