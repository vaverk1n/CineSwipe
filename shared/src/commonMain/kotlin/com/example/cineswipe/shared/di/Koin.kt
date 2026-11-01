package com.example.cineswipe.shared.di

import com.example.cineswipe.shared.core.DefaultDispatcherProvider
import com.example.cineswipe.shared.core.DispatcherProvider
import com.example.cineswipe.shared.core.LogLevel
import com.example.cineswipe.shared.core.Logger
import com.example.cineswipe.shared.core.NapierLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/** Базовые зависимости слоя core. Остальные модули (data, domain, presentation) добавятся по мере появления. */
fun coreModule(isDebug: Boolean) = module {
    single<DispatcherProvider> { DefaultDispatcherProvider() }
    single<Logger> {
        NapierLogger(minLevel = if (isDebug) LogLevel.DEBUG else LogLevel.WARNING)
    }
}

/**
 * Единая точка запуска Koin. Платформа передаёт свои настройки через [appDeclaration]
 * (на Android это androidContext и androidModule).
 */
fun initKoin(
    isDebug: Boolean,
    appDeclaration: KoinAppDeclaration = {},
): KoinApplication = startKoin {
    appDeclaration()
    modules(coreModule(isDebug))
}