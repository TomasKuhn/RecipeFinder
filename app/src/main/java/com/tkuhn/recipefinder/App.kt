package com.tkuhn.recipefinder

import android.app.Application
import com.tkuhn.recipefinder.di.cacheModule
import com.tkuhn.recipefinder.di.mappersModule
import com.tkuhn.recipefinder.di.repoModule
import com.tkuhn.recipefinder.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(repoModule, uiModule, mappersModule, cacheModule))
        }
    }

    companion object {

        lateinit var instance: App

    }
}