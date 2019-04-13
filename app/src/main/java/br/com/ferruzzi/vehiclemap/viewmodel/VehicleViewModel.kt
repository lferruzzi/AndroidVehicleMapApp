package br.com.ferruzzi.vehiclemap.viewmodel

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import br.com.ferruzzi.vehiclemap.repository.VehicleRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.properties.Delegates.observable

/**
 * [AndroidViewModel] containing the [vehicles] information within the [currentMapBounds] to be presented.
 */
class VehicleViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val vehicleRepository: VehicleRepository by inject()

    val vehicles = vehicleRepository.vehicles
    val wasLastRequestSuccessful = vehicleRepository.wasLastRequestSuccessful
    val isRequestingNewVehicles = vehicleRepository.isRequestingNewVehicles
    var currentMapBounds by observable(initialValue = CAMPINAS_LATLONG_BOUNDS as? LatLngBounds,
            onChange = { _, _, newBounds -> vehicleRepository.requestVehiclesByBounds(newBounds) })

    companion object {
        private const val CAMPINAS_LATITUDE1: Double = -22.85833
        private const val CAMPINAS_LONGITUDE1: Double = -47.22
        private const val CAMPINAS_LATITUDE2: Double = -22.97056
        private const val CAMPINAS_LONGITUDE2: Double = -46.99583

        /**
         * Campinas's [LatLngBounds] used to initialize the [currentMapBounds].
         */
        private val CAMPINAS_LATLONG_BOUNDS: LatLngBounds = LatLngBounds.Builder()
            .include(LatLng(CAMPINAS_LATITUDE1, CAMPINAS_LONGITUDE1))
            .include(LatLng(CAMPINAS_LATITUDE2, CAMPINAS_LONGITUDE2))
            .build()
    }
}
