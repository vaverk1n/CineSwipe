package com.example.cineswipe.shared.core

//Слой приложения, из которого пришла запись.
enum class LogTag { NET, DB, VM, UI }

enum class LogLevel { DEBUG, INFO, WARNING, ERROR }

interface Logger {
    fun log(
        level: LogLevel,
        tag: LogTag,
        event: String,
        screen: String? = null,
        details: String? = null,
        throwable: Throwable? = null,
    )
}

fun Logger.d(   //debug
    tag: LogTag,
    event: String,
    screen: String? = null,
    details: String? = null
) = log(LogLevel.DEBUG, tag, event, screen, details)

fun Logger.i(   //info
    tag: LogTag,
    event: String,
    screen: String? = null,
    details: String? = null
) = log(LogLevel.INFO, tag, event, screen, details)

fun Logger.w(   //warning
    tag: LogTag,
    event: String,
    screen: String? = null,
    details: String? = null
) = log(LogLevel.WARNING, tag, event, screen, details)

fun Logger.e(   //error
    tag: LogTag,
    event: String,
    screen: String? = null,
    details: String? = null,
    throwable: Throwable? = null,
) = log(LogLevel.ERROR, tag, event, screen, details, throwable)

//Заглушка для тестов и мест, где логирование не нужно.
object NoOpLogger : Logger {
    override fun log(
        level: LogLevel,
        tag: LogTag,
        event: String,
        screen: String?,
        details: String?,
        throwable: Throwable?,
    ) = Unit
}