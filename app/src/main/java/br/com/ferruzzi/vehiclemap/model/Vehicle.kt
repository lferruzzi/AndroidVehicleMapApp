package br.com.ferruzzi.vehiclemap.model

import android.location.Address
import com.google.gson.annotations.SerializedName

/**
 * Data class to represent a vehicle, containing:
 * The [id] of the vehicle on the server;
 * The current [coordinate] of the vehicle on the map;
 * The [fleetType] of that vehicle;
 * The direction that the vehicle is [heading] in degrees;
 * The approximated [address] based on the current [coordinate].
 */
data class Vehicle(
    @SerializedName("id")
    val id: Int,
    @SerializedName("coordinate")
    val coordinate: Coordinate,
    @SerializedName("fleetType")
    val fleetType: String,
    @SerializedName("heading")
    val heading: Double,
    var address: Address?
) {
    companion object {
        const val TAXI_FLEET_TYPE = "TAXI"
        const val POOLING_FLEET_TYPE = "POOLING"
    }
}
