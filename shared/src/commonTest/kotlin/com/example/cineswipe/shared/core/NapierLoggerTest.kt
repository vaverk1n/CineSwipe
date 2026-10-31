package com.example.cineswipe.shared.core

import io.github.aakira.napier.Antilog
import io.github.aakira.napier.Napier
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import io.github.aakira.napier.LogLevel as NapierLevel

class NapierLoggerTest {

    private class RecordingAntilog : Antilog() {
        val messages = mutableListOf<String?>()
        override fun performLog(
            priority: NapierLevel,
            tag: String?,
            throwable: Throwable?,
            message: String?,
        ) {
            messages += message
        }
    }

    private val antilog = RecordingAntilog()

    @BeforeTest
    fun setUp() = Napier.base(antilog)

    @AfterTest
    fun tearDown() = Napier.takeLogarithm()

    @Test
    fun `when level is below minLevel, then record is dropped`() {
        val logger = NapierLogger(minLevel = LogLevel.WARNING, clock = { 0L })

        logger.d(LogTag.UI, "tap")
        logger.w(LogTag.UI, "slow frame")

        assertEquals(listOf<String?>("00:00:00.000 | UI | - | slow frame | -"), antilog.messages)
    }

    @Test
    fun `when details contain a token, then it does not reach the log`() {
        val logger = NapierLogger(minLevel = LogLevel.DEBUG, clock = { 0L })

        logger.i(LogTag.NET, "request", details = "Bearer SECRET")

        assertFalse(antilog.messages.single()!!.contains("SECRET"))
    }
}