package com.example.cineswipe.shared.presentation.base

/** Неизменяемое состояние экрана. */
interface UiState

/** Событие от View к ViewModel (тап, жест, повтор). */
interface UiEvent

/** Одноразовое действие ViewModel → View (навигация, Snackbar). */
interface UiEffect