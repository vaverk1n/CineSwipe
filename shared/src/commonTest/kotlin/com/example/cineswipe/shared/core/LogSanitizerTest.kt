package com.example.cineswipe.shared.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class LogSanitizerTest {

    @Test
    fun `when text contains bearer token, then token is masked`() {
        val result = LogSanitizer.sanitize("Authorization: Bearer abc.def-123")

        assertEquals("Authorization: Bearer ***", result)
    }

    @Test
    fun `when text contains api_key parameter, then value is masked`() {
        val result = LogSanitizer.sanitize("GET /movie/1?api_key=SECRET&language=ru")

        assertFalse(result.contains("SECRET"))
        assertEquals("GET /movie/1?api_key=***&language=ru", result)
    }

    @Test
    fun `when text has no secrets, then it is unchanged`() {
        val text = "GET /discover/movie 200 312ms"

        assertEquals(text, LogSanitizer.sanitize(text))
    }
}