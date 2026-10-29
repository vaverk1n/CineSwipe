package com.example.cineswipe.shared.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/** Боевая реализация: настоящие диспетчеры. */
class DefaultDispatcherProvider : DispatcherProvider {
    override val main: CoroutineDispatcher = Dispatchers.Main
    override val io: CoroutineDispatcher = ioDispatcher
    override val default: CoroutineDispatcher = Dispatchers.Default
}