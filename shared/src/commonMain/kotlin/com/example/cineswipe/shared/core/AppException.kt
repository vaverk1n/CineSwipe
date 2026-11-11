package com.example.cineswipe.shared.core

class AppException(
    val error: AppError,
    cause: Throwable? = null
    ) : Exception("AppError: $error", cause)
