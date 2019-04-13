package br.com.ferruzzi.vehiclemap.rest

import br.com.ferruzzi.vehiclemap.model.VehicleList
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Query

/**
 * Interface to communicate with the server containing the vehicles information.
 */
interface VehicleEndpointInterface {

    /**
     * Retrieves the [VehicleList] on the server based on the bounds of a map defined by two points
     * ([latitude1], [longitude1]) and ([latitude2], [longitude2]).
     */
    @GET("/")
    fun getVehiclesByBounds(
        @Query("p1Lat") latitude1: Double,
        @Query("p1Lon") longitude1: Double,
        @Query("p2Lat") latitude2: Double,
        @Query("p2Lon") longitude2: Double
    ): Call<VehicleList>
}
