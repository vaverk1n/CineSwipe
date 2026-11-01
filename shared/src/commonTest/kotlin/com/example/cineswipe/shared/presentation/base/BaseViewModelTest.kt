package com.example.cineswipe.shared.presentation.base

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

private data class CounterState(val count: Int = 0) : UiState

private sealed interface CounterEvent : UiEvent {
    data object Increment : CounterEvent
    data object Reset : CounterEvent
}

private sealed interface CounterEffect : UiEffect {
    data class ShowMessage(val text: String) : CounterEffect
}

private class CounterViewModel :
    BaseViewModel<CounterState, CounterEvent, CounterEffect>(CounterState()) {

    override fun onEvent(event: CounterEvent) {
        when (event) {
            CounterEvent.Increment -> {
                updateState { copy(count = count + 1) }
                if (currentState.count == 3) sendEffect(CounterEffect.ShowMessage("Три!"))
            }
            CounterEvent.Reset -> updateState { copy(count = 0) }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class BaseViewModelTest {

    @BeforeTest
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @AfterTest
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `when created, then state equals initial state`() {
        val viewModel = CounterViewModel()

        assertEquals(CounterState(0), viewModel.state.value)
    }

    @Test
    fun `when events are sent, then state is updated in order`() {
        val viewModel = CounterViewModel()

        viewModel.onEvent(CounterEvent.Increment)
        viewModel.onEvent(CounterEvent.Increment)
        assertEquals(2, viewModel.state.value.count)

        viewModel.onEvent(CounterEvent.Reset)
        assertEquals(0, viewModel.state.value.count)
    }

    @Test
    fun `when counter reaches three, then effect is emitted once`() = runTest {
        val viewModel = CounterViewModel()

        viewModel.effects.test {
            repeat(4) { viewModel.onEvent(CounterEvent.Increment) }

            assertEquals(CounterEffect.ShowMessage("Три!"), awaitItem())
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when effect was already consumed, then new collector does not receive it`() = runTest {
        val viewModel = CounterViewModel()
        repeat(3) { viewModel.onEvent(CounterEvent.Increment) }

        viewModel.effects.test {
            assertEquals(CounterEffect.ShowMessage("Три!"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        viewModel.effects.test {
            expectNoEvents()
            cancelAndIgnoreRemainingEvents()
        }
    }
}