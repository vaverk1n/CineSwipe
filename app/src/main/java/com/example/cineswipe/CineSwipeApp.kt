package com.example.cineswipe

import android.app.Application
import com.example.cineswipe.di.androidModule
import com.example.cineswipe.shared.di.initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext

class CineSwipeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        initKoin(isDebug = BuildConfig.DEBUG) {
            androidContext(this@CineSwipeApp)
            modules(androidModule)
        }
    }
}