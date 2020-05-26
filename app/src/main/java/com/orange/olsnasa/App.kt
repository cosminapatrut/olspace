package com.orange.olsnasa


import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.orange.olsnasa.instances.Instances
import timber.log.Timber



class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Enable vector drawables.
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // Initialize dependency injection.
        Instances.init(this)
        Timber.plant(Timber.DebugTree())
    }
}