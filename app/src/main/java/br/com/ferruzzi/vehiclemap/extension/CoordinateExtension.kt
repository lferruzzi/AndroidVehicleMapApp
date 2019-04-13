package br.com.ferruzzi.vehiclemap.extension

import android.content.Context
import android.location.Geocoder
import br.com.ferruzzi.vehiclemap.model.Coordinate
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.Locale

/**
 * Return the approximated address based on the [Coordinate.latitude] and [Coordinate.longitude],
 * this is an IO request executed synchronously, make sure to execute the call on a background thread.
 *
 * In case of an [IOException] a null value will be returned.
 * In case of an invalid or unknown address a null value will be returned.
 */
fun Coordinate.getAddress(context: Context) =
    try {
        Geocoder(context, Locale.getDefault()).getFromLocation(latitude, longitude, 1).firstOrNull()
    } catch (e: IOException) {
        null
    }

/**
 * Return the equivalent [LatLng] based on the [Coordinate.latitude] and [Coordinate.longitude], used by GoogleMaps.
 */
fun Coordinate.getLatLng() = LatLng(latitude, longitude)
