package com.hiring.cleanarchitecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
//        startKoin {
//            androidContext(this@App)
//            androidLogger()
//            modules(allModules)
//        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
