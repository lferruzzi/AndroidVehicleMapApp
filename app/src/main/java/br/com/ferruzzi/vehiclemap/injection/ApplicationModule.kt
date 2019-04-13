package br.com.ferruzzi.vehiclemap.injection

import br.com.ferruzzi.vehiclemap.adapter.VehicleListAdapter
import br.com.ferruzzi.vehiclemap.repository.VehicleRepository
import br.com.ferruzzi.vehiclemap.rest.VehicleWebClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

/**
 * Application Koin module.
 */
object ApplicationModule {
    fun getModule(): Module = module {
        single { VehicleRepository(context = get(), vehicleWebClient = get()) }
        single { VehicleWebClient() }
        scope(MAIN_ACTIVITY_SCOPE) { VehicleListAdapter() }
    }

    const val MAIN_ACTIVITY_SCOPE = "MAIN_ACTIVITY_SCOPE"
}
