package com.example.cineswipe.shared.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * База для ViewModel экранов: одно состояние [S], входящие события [E], одноразовые эффекты [F].
 * Изменяемые потоки скрыты внутри, наружу отдаются только чтение.
 */
abstract class BaseViewModel<S : UiState, E : UiEvent, F : UiEffect>(
    initialState: S,
) : ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // Channel, а не SharedFlow: эффект не теряется, если View в момент отправки не подписана,
    // и не повторяется после пересоздания экрана.
    private val _effects = Channel<F>(Channel.BUFFERED)
    val effects: Flow<F> = _effects.receiveAsFlow()

    /** Единственная точка входа для View. */
    abstract fun onEvent(event: E)

    protected val currentState: S get() = _state.value

    /** Атомарно обновляет состояние: `updateState { copy(isLoading = false) }`. */
    protected fun updateState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected fun sendEffect(effect: F) {
        viewModelScope.launch { _effects.send(effect) }
    }
}