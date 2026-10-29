package com.example.cineswipe.shared.core

import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class DispatcherProviderTest {

    // Учебный класс: так в проекте будут выглядеть репозитории и use cases
    private class Loader(private val dispatchers: DispatcherProvider) {
        suspend fun load(): Int = withContext(dispatchers.io) { 42 }
    }

    @Test
    fun `when TestDispatcherProvider is created, then all dispatchers are the same`() {
        val dispatcher = StandardTestDispatcher()

        val provider = TestDispatcherProvider(dispatcher)

        assertSame(dispatcher, provider.main)
        assertSame(dispatcher, provider.io)
        assertSame(dispatcher, provider.default)
    }

    @Test
    fun `when class uses TestDispatcherProvider, then it runs on the test scheduler`() = runTest {
        val dispatchers = TestDispatcherProvider(StandardTestDispatcher(testScheduler))

        val result = Loader(dispatchers).load()

        assertEquals(42, result)
    }
}