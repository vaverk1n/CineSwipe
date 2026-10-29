package com.example.cineswipe.shared.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

/** Тестовая реализация: все диспетчеры указывают на один тестовый диспетчер. */
class TestDispatcherProvider(
    testDispatcher: CoroutineDispatcher = StandardTestDispatcher(),
) : DispatcherProvider {
    override val main: CoroutineDispatcher = testDispatcher
    override val io: CoroutineDispatcher = testDispatcher
    override val default: CoroutineDispatcher = testDispatcher
}