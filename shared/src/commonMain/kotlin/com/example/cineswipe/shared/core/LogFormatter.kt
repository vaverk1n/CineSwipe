package com.example.cineswipe.shared.core

object LogFormatter {

    // «время | тег | экран | событие | детали»; отсутствующие поля заменяются на «-».
    fun format(
        timeMillis: Long,
        tag: LogTag,
        event: String,
        screen: String?,
        details: String?,
    ): String = listOf(
        formatTime(timeMillis),
        tag.name,
        screen ?: "-",
        event,
        details ?: "-",
    ).joinToString(" | ")

    // Время суток в UTC в формате HH:mm:ss.SSS.
    internal fun formatTime(millis: Long): String {
        val dayMs = millis.mod(86_400_000L)
        val hours = dayMs / 3_600_000
        val minutes = dayMs % 3_600_000 / 60_000
        val seconds = dayMs % 60_000 / 1_000
        val ms = dayMs % 1_000
        return "${hours.pad(2)}:${minutes.pad(2)}:${seconds.pad(2)}.${ms.pad(3)}"
    }

    private fun Long.pad(length: Int) = toString().padStart(length, '0')
}