package br.com.ferruzzi.vehiclemap.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import br.com.ferruzzi.vehiclemap.extension.getAddress
import br.com.ferruzzi.vehiclemap.model.Vehicle
import br.com.ferruzzi.vehiclemap.model.VehicleList
import br.com.ferruzzi.vehiclemap.rest.VehicleWebClient
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Class containing the current information of the [VehicleList].
 */
class VehicleRepository(private val context: Context, private val vehicleWebClient: VehicleWebClient) {

    private var requestAddressJob: Job? = null

    /**
     * The current [Vehicle] collection.
     */
    val vehicles = MutableLiveData<List<Vehicle>>()

    /**
     * The last server request status.
     */
    val wasLastRequestSuccessful = MutableLiveData<Boolean>()

    /**
     * True if a new server request is ongoing, false otherwise.
     */
    val isRequestingNewVehicles = MutableLiveData<Boolean>()

    /**
     * Requests a new collection of [vehicles] based on the [mapBounds].
     */
    fun requestVehiclesByBounds(mapBounds: LatLngBounds?) {
        isRequestingNewVehicles.value = true

        mapBounds?.apply {
            vehicleWebClient
                .vehicleEndpoint()
                .getVehiclesByBounds(northeast.latitude, northeast.longitude, southwest.latitude, southwest.longitude)
                .enqueue(object : Callback<VehicleList?> {

                    override fun onResponse(call: Call<VehicleList?>, response: Response<VehicleList?>) {
                        onVehicleServerResponse(response)
                    }

                    override fun onFailure(call: Call<VehicleList?>?, t: Throwable?) {
                        onVehicleServerRequestError()
                    }
                })
        }
    }

    private fun onVehicleServerResponse(response: Response<VehicleList?>) {
        wasLastRequestSuccessful.value = response.isSuccessful
        isRequestingNewVehicles.value = false
        response.body()?.let {
            vehicles.value = it.vehicles
            populateVehiclesAddress(it.vehicles)
        }
    }

    private fun onVehicleServerRequestError() {
        requestAddressJob?.cancel()
        vehicles.value = listOf()
        wasLastRequestSuccessful.value = false
        isRequestingNewVehicles.value = false
    }

    private fun populateVehiclesAddress(vehicleList: List<Vehicle>) {
        requestAddressJob?.cancel()
        requestAddressJob = GlobalScope.launch {
            vehicleList.forEach {
                it.address = it.coordinate.getAddress(context)
                withContext(Dispatchers.Main) {
                    vehicles.value = vehicleList
                }
            }
        }
        requestAddressJob?.start()
    }
}
