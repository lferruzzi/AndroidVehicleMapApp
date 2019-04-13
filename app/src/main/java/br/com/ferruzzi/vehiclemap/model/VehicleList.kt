package br.com.ferruzzi.vehiclemap.model

import com.google.gson.annotations.SerializedName

/**
 * Data class containing all the [Vehicle]s retrieved.
 */
data class VehicleList(
    @SerializedName("poiList")
    val vehicles: List<Vehicle>
)
