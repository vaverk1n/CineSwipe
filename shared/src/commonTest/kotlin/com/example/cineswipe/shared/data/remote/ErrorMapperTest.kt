package com.example.cineswipe.shared.data.remote

import com.example.cineswipe.shared.core.AppError
import com.example.cineswipe.shared.core.AppException
import com.example.cineswipe.shared.core.AppResult
import com.example.cineswipe.shared.core.runCatchingApp
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.get
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ErrorMapperTest {

    private suspend fun errorFor(status: HttpStatusCode, headers: Headers = headersOf()): AppError {
        val client = HttpClient(MockEngine { respond("", status, headers) }) {
            expectSuccess = true
        }
        return try {
            client.get("https://test.local")
            error("Ожидалось исключение")
        } catch (e: ResponseException) {
            e.toAppException().error
        }
    }

    @Test
    fun `when status is 401, then Unauthorized`() = runTest {
        assertEquals(AppError.Unauthorized, errorFor(HttpStatusCode.Unauthorized))
    }

    @Test
    fun `when status is 404, then NotFound`() = runTest {
        assertEquals(AppError.NotFound, errorFor(HttpStatusCode.NotFound))
    }

    @Test
    fun `when status is 429 with numeric Retry-After, then RateLimited with seconds`() = runTest {
        val error = errorFor(HttpStatusCode.TooManyRequests, headersOf(HttpHeaders.RetryAfter, "7"))
        assertEquals(AppError.RateLimited(7), error)
    }

    @Test
    fun `when status is 429 without Retry-After, then RateLimited with null`() = runTest {
        assertEquals(AppError.RateLimited(null), errorFor(HttpStatusCode.TooManyRequests))
    }

    @Test
    fun `when status is 429 with non-numeric Retry-After, then RateLimited with null`() = runTest {
        val error = errorFor(HttpStatusCode.TooManyRequests, headersOf(HttpHeaders.RetryAfter, "Wed, 21 Oct 2026"))
        assertEquals(AppError.RateLimited(null), error)
    }

    @Test
    fun `when status is 500, then Server`() = runTest {
        assertEquals(AppError.Server(500), errorFor(HttpStatusCode.InternalServerError))
    }

    @Test
    fun `when status is other 4xx, then Unknown`() = runTest {
        val customStatus = HttpStatusCode(418, "I'm a teapot")
        assertTrue(errorFor(customStatus) is AppError.Unknown)
    }
    @Test
    fun `when SerializationException, then Parse`() {
        val error = SerializationException("bad json").toAppException().error
        assertEquals(AppError.Parse, error)
    }

    @Test
    fun `when IOException, then NoNetwork`() {
        val error = IOException("no route").toAppException().error
        assertEquals(AppError.NoNetwork, error)
    }

    @Test
    fun `when runCatchingApp catches AppException, then it returns Failure with correct error`() {
        val result = runCatchingApp { throw AppException(AppError.NotFound) }
        assertTrue(result is AppResult.Failure)
        assertEquals(AppError.NotFound, result.error)
    }
}