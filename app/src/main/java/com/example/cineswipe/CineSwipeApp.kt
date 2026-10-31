package com.example.cineswipe

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class CineSwipeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog()) // Вывод логов в Logcat
    }
}