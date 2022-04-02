package com.hir0mu.cleanarchitecture

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.hir0mu.cleanarchitecture.util.NetworkManagerImpl
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        NetworkManagerImpl.observe(manager)
    }
}
