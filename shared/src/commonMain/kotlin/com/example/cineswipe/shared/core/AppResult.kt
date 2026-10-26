package com.example.cineswipe.shared.core

/**
 * Результат операции, которая может завершиться ошибкой: либо [Success] с данными,
 * либо [Failure] с типизированной ошибкой [AppError].
 * Состояния загрузки здесь нет: оно относится к экрану и хранится в UiState.
 */
sealed interface AppResult<out T> {

    /** Успех; [data] — полученные данные. */
    data class Success<out T>(val data: T) : AppResult<T>

    /** Ошибка; [error] описывает, что именно пошло не так. */
    data class Failure(val error: AppError) : AppResult<Nothing>
}

/** Преобразует данные успешного результата; ошибка проходит дальше без изменений. */
inline fun <T, R> AppResult<T>.map(transform: (T) -> R): AppResult<R> = when (this) {
    is AppResult.Success -> AppResult.Success(transform(data))
    is AppResult.Failure -> this
}

/** Как [map], но для шага, который сам возвращает [AppResult]; убирает вложенность. */
inline fun <T, R> AppResult<T>.flatMap(transform: (T) -> AppResult<R>): AppResult<R> = when (this) {
    is AppResult.Success -> transform(data)
    is AppResult.Failure -> this
}

/** Сводит оба варианта результата к одному значению типа [R]. */
inline fun <T, R> AppResult<T>.fold(
    onSuccess: (T) -> R,
    onFailure: (AppError) -> R,
): R = when (this) {
    is AppResult.Success -> onSuccess(data)
    is AppResult.Failure -> onFailure(error)
}

/** Выполняет [action] для успешного результата и возвращает тот же результат. */
inline fun <T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
    if (this is AppResult.Success) action(data)
    return this
}

/** Выполняет [action] для ошибки и возвращает тот же результат. */
inline fun <T> AppResult<T>.onFailure(action: (AppError) -> Unit): AppResult<T> {
    if (this is AppResult.Failure) action(error)
    return this
}

/** Возвращает данные при успехе или `null` при ошибке. */
fun <T> AppResult<T>.getOrNull(): T? = (this as? AppResult.Success)?.data