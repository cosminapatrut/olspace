package com.orange.olsnasa.instances

import android.app.Application
import com.orange.olsnasa.instances.modules.defaultModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * Main class to handle injected dependencies. Load modules according to build configuration.
 * Call reset to initialize modules according to current configuration.
 */
object Instances {
    lateinit var koinApp: KoinApplication

    /**
     * Uses default module.
     */
    private fun useDefaults() = loadKoinModules(
        defaultModule
    )

    /**
     * Initialize modules according to current build configuration.
     */
    fun init(application: Application) {
        koinApp = startKoin {
            androidContext(application)
            useDefaults()
        }
    }
}