package com.example.cineswipe.shared.core

import io.github.aakira.napier.Napier
import io.github.aakira.napier.LogLevel as NapierLevel

/**
 * Реализация Logger поверх Napier. Записи ниже [minLevel] отбрасываются:
 * в release-сборке задаётся WARNING, в debug — DEBUG.
 */
class NapierLogger(
    private val minLevel: LogLevel,
    private val clock: () -> Long = ::currentTimeMillis,
) : Logger {

    override fun log(
        level: LogLevel,
        tag: LogTag,
        event: String,
        screen: String?,
        details: String?,
        throwable: Throwable?,
    ) {
        if (level < minLevel) return
        val message = LogFormatter.format(
            timeMillis = clock(),
            tag = tag,
            event = LogSanitizer.sanitize(event),
            screen = screen,
            details = details?.let(LogSanitizer::sanitize),
        )
        Napier.log(
            priority = level.toNapier(),
            tag = tag.name,
            throwable = throwable,
            message = message,
        )
    }

    private fun LogLevel.toNapier(): NapierLevel = when (this) {
        LogLevel.DEBUG -> NapierLevel.DEBUG
        LogLevel.INFO -> NapierLevel.INFO
        LogLevel.WARNING -> NapierLevel.WARNING
        LogLevel.ERROR -> NapierLevel.ERROR
    }
}