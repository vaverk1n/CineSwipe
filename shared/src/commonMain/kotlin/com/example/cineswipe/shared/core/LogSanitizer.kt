package com.example.cineswipe.shared.core

object LogSanitizer {
    private val bearer = Regex("(?i)(bearer\\s+)[A-Za-z0-9._~+/=-]+")
    private val apiKey = Regex("(?i)(api_key=)[^&\\s]+")

    fun sanitize(text: String): String =
        text.replace(bearer, "\$1***").replace(apiKey, "\$1***")
}