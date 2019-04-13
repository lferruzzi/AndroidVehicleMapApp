package br.com.ferruzzi.vehiclemap.model

import com.google.gson.annotations.SerializedName

/**
 * Data class to represent a coordinate using [latitude] and [longitude].
 */
data class Coordinate(
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)
