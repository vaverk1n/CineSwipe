package com.example.cineswipe.shared.core

import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class AppResultTest {

    // map

    @Test
    fun `when map is called on Success, then transforms data`() {
        val result = AppResult.Success(5)

        val mapped = result.map { it * 2 }

        assertTrue(mapped is AppResult.Success)
        assertEquals(10, mapped.data)
    }

    @Test
    fun `when map is called on Failure, then returns same Failure without calling transform`() {
        val result = AppResult.Failure(AppError.NoNetwork)
        var transformCalled = false

        val mapped = result.map {
            transformCalled = true
            10
        }

        assertSame(result, mapped)
        assertFalse(transformCalled)
    }

    // flatMap

    @Test
    fun `when flatMap is called on Success, then returns result of transform`() {
        val result = AppResult.Success(5)

        val flatMapped = result.flatMap { AppResult.Success(it * 3) }

        assertTrue(flatMapped is AppResult.Success)
        assertEquals(15, flatMapped.data)
    }

    @Test
    fun `when flatMap transform returns Failure, then result is that Failure`() {
        val result = AppResult.Success(5)

        val flatMapped = result.flatMap<Int, Int> { AppResult.Failure(AppError.NotFound) }

        assertTrue(flatMapped is AppResult.Failure)
        assertEquals(AppError.NotFound, flatMapped.error)
    }

    @Test
    fun `when flatMap is called on Failure, then returns same Failure without calling transform`() {
        val result = AppResult.Failure(AppError.Server(500))
        var transformCalled = false

        val flatMapped = result.flatMap {
            transformCalled = true
            AppResult.Success(10)
        }

        assertSame(result, flatMapped)
        assertFalse(transformCalled)
    }

    // fold

    @Test
    fun `when fold is called on Success, then calls onSuccess`() {
        val result = AppResult.Success(5)

        val folded = result.fold(
            onSuccess = { "success: $it" },
            onFailure = { "failure" },
        )

        assertEquals("success: 5", folded)
    }

    @Test
    fun `when fold is called on Failure, then calls onFailure with the error`() {
        val result = AppResult.Failure(AppError.Parse)

        val folded = result.fold(
            onSuccess = { null },
            onFailure = { it },
        )

        assertEquals(AppError.Parse, folded)
    }

    // onSuccess / onFailure

    @Test
    fun `when onSuccess is called on Success, then executes action`() {
        val result = AppResult.Success(5)
        var received = 0

        result.onSuccess { received = it }

        assertEquals(5, received)
    }

    @Test
    fun `when onSuccess is called on Failure, then does not execute action`() {
        val result = AppResult.Failure(AppError.NotFound)
        var actionCalled = false

        result.onSuccess { actionCalled = true }

        assertFalse(actionCalled)
    }

    @Test
    fun `when onFailure is called on Failure, then executes action`() {
        val result = AppResult.Failure(AppError.Unauthorized)
        var captured: AppError? = null

        result.onFailure { captured = it }

        assertEquals(AppError.Unauthorized, captured)
    }

    @Test
    fun `when onFailure is called on Success, then does not execute action`() {
        val result = AppResult.Success(5)
        var actionCalled = false

        result.onFailure { actionCalled = true }

        assertFalse(actionCalled)
    }

    @Test
    fun `when onSuccess and onFailure are chained, then they return the same result`() {
        val success = AppResult.Success(5)
        val failure = AppResult.Failure(AppError.NoNetwork)

        assertSame(success, success.onSuccess { }.onFailure { })
        assertSame(failure, failure.onSuccess { }.onFailure { })
    }

    // getOrNull

    @Test
    fun `when getOrNull is called on Success, then returns data`() {
        val result = AppResult.Success("Hello")

        assertEquals("Hello", result.getOrNull())
    }

    @Test
    fun `when getOrNull is called on Failure, then returns null`() {
        val result = AppResult.Failure(AppError.Parse)

        assertNull(result.getOrNull())
    }

    // runCatchingApp

    @Test
    fun `when runCatchingApp block succeeds, then returns Success`() {
        val result = runCatchingApp { 42 }

        assertTrue(result is AppResult.Success)
        assertEquals(42, result.data)
    }

    @Test
    fun `when runCatchingApp block throws, then returns Failure with Unknown and original cause`() {
        val exception = IllegalStateException("Something broke")

        val result = runCatchingApp<Int> { throw exception }

        assertTrue(result is AppResult.Failure)
        val error = result.error
        assertTrue(error is AppError.Unknown)
        assertSame(exception, error.cause)
    }

    @Test
    fun `when runCatchingApp block throws CancellationException, then rethrows it`() {
        assertFailsWith<CancellationException> {
            runCatchingApp<Int> { throw CancellationException("Coroutine cancelled") }
        }
    }

    // toAppError

    @Test
    fun `when toAppError is called, then returns Unknown with the same cause`() {
        val exception = RuntimeException("boom")

        val error = exception.toAppError()

        assertTrue(error is AppError.Unknown)
        assertSame(exception, error.cause)
    }
}