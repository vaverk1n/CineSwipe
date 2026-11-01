package com.example.cineswipe.shared.di

import com.example.cineswipe.shared.core.DefaultDispatcherProvider
import com.example.cineswipe.shared.core.DispatcherProvider
import com.example.cineswipe.shared.core.Logger
import com.example.cineswipe.shared.core.NapierLogger
import org.koin.dsl.koinApplication
import kotlin.test.Test
import kotlin.test.assertSame
import kotlin.test.assertTrue

class KoinModulesTest {

    @Test
    fun `when core module is loaded, then all core dependencies resolve`() {
        val koin = koinApplication { modules(coreModule(isDebug = true)) }.koin

        assertTrue(koin.get<DispatcherProvider>() is DefaultDispatcherProvider)
        assertTrue(koin.get<Logger>() is NapierLogger)
    }

    @Test
    fun `when dependency is requested twice, then the same instance is returned`() {
        val koin = koinApplication { modules(coreModule(isDebug = false)) }.koin

        assertSame(koin.get<Logger>(), koin.get<Logger>())
    }
}