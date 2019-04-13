package br.com.ferruzzi.vehiclemap.rest

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

/**
 * Web client that connects to the server containing the vehicles information through the [VehicleEndpointInterface].
 */
class VehicleWebClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(VEHICLE_SERVER_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /**
     * Endpoint to communicate with the server and retrieve the vehicle data.
     */
    fun vehicleEndpoint(): VehicleEndpointInterface = retrofit.create(VehicleEndpointInterface::class.java)

    companion object {
        private const val VEHICLE_SERVER_URL = "https://censored-url-check-private-repository/"
    }
}
