package br.com.ferruzzi.vehiclemap.extension

import br.com.ferruzzi.vehiclemap.R
import br.com.ferruzzi.vehiclemap.model.Vehicle

/**
 * Return an icon resource based on the [Vehicle.fleetType].
 */
fun Vehicle.getIcon() = when (fleetType) {
    Vehicle.POOLING_FLEET_TYPE -> R.drawable.ic_pooling
    Vehicle.TAXI_FLEET_TYPE -> R.drawable.ic_taxi
    else -> R.drawable.ic_car
}

/**
 * Return an map marker icon resource based on the [Vehicle.fleetType].
 */
fun Vehicle.getMapMarkerIcon() = when (fleetType) {
    Vehicle.POOLING_FLEET_TYPE -> R.drawable.ic_marker_pool_map
    Vehicle.TAXI_FLEET_TYPE -> R.drawable.ic_marker_taxi_map
    else -> R.drawable.ic_marker_car_map
}
