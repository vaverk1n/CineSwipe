package com.example.cineswipe.shared.core

import kotlin.test.Test
import kotlin.test.assertEquals

class LogFormatterTest {

    @Test
    fun `when all fields are present, then line follows time tag screen event details order`() {
        val line = LogFormatter.format(
            timeMillis = 3_723_045L,
            tag = LogTag.NET,
            event = "request",
            screen = "SwipeScreen",
            details = "GET /discover 200 312ms",
        )

        assertEquals("01:02:03.045 | NET | SwipeScreen | request | GET /discover 200 312ms", line)
    }

    @Test
    fun `when screen and details are null, then dashes are used`() {
        val line = LogFormatter.format(0L, LogTag.UI, "tap", null, null)

        assertEquals("00:00:00.000 | UI | - | tap | -", line)
    }

    @Test
    fun `when time exceeds one day, then it wraps around`() {
        assertEquals("00:00:01.000", LogFormatter.formatTime(86_400_000L + 1_000L))
    }
}