package com.example.cineswipe.shared

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

// Тестовая модель для проверки сериализации
@Serializable
private data class PingResponse(
    val message: String,
    val code: Int
)

class SetupSmokeTest {

    @Test
    fun `when client receives valid json then it parses into model`() = runTest {
        // 1. Настраиваем MockEngine, который отвечает 200 OK и JSON-строкой
        val engine = MockEngine { request ->
            respond(
                content = """{"message": "pong", "code": 200}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        // 2. Создаем HttpClient с плагином ContentNegotiation
        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        // 3. Выполняем запрос и парсим ответ в модель
        val response: PingResponse = client.get("https://example.test/ping").body()

        // 4. Проверяем результат
        assertEquals("pong", response.message)
        assertEquals(200, response.code)
    }

    @Test
    fun `when json contains unknown keys then it parses ignoring them`() = runTest {
        // 1. JSON содержит лишнее поле "extraField", которого нет в PingResponse
        val engine = MockEngine {
            respond(
                content = """{"message": "pong", "code": 200, "extraField": "ignored"}""",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        // 2. Плагин настроен с ignoreUnknownKeys = true
        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        // 3. Запрос должен успешно выполниться, несмотря на лишнее поле
        val response: PingResponse = client.get("https://example.test/ping").body()

        assertEquals("pong", response.message)
        assertEquals(200, response.code)
    }
}