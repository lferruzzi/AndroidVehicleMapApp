package br.com.ferruzzi.vehiclemap

import android.app.Application
import br.com.ferruzzi.vehiclemap.injection.ApplicationModule
import org.koin.android.ext.android.startKoin

/**
 * First class to be executed when the application starts.
 */
class VehicleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    /**
     * Initialize Koin's application module.
     */
    private fun initializeKoin() {
        startKoin(this, listOf(ApplicationModule.getModule()))
    }
}
