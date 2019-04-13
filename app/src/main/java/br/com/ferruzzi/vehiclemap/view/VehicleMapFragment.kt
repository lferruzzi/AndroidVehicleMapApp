package br.com.ferruzzi.vehiclemap.view

import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.ferruzzi.vehiclemap.adapter.VehicleListAdapter
import br.com.ferruzzi.vehiclemap.extension.getLatLng
import br.com.ferruzzi.vehiclemap.extension.getMapMarkerIcon
import br.com.ferruzzi.vehiclemap.injection.ApplicationModule.MAIN_ACTIVITY_SCOPE
import br.com.ferruzzi.vehiclemap.model.Vehicle
import br.com.ferruzzi.vehiclemap.viewmodel.VehicleViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnCameraIdleListener
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback
import com.google.android.gms.maps.MapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

/**
 * [Fragment] to show a map with the [Vehicle]s' position marked on it.
 */
class VehicleMapFragment : MapFragment(), OnMapReadyCallback, OnCameraIdleListener, OnMapLoadedCallback {
    private val vehicleListAdapter: VehicleListAdapter
            by inject(scope = getKoin().getOrCreateScope(MAIN_ACTIVITY_SCOPE))

    private lateinit var vehicleViewModel: VehicleViewModel

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        getMapAsync(this)
    }

    private fun initializeViewModel() {
        vehicleViewModel = ViewModelProviders.of(this.activity as FragmentActivity).get(VehicleViewModel::class.java)

        vehicleViewModel.vehicles.observe(this.activity as FragmentActivity, Observer<List<Vehicle>> {
            vehicleListAdapter.vehicles = it
            addVehicleMarkers(it)
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map?.setOnMapLoadedCallback(this)
    }

    override fun onMapLoaded() {
        map?.apply {
            vehicleListAdapter.map = map
            moveCamera(CameraUpdateFactory.newLatLngBounds(vehicleViewModel.currentMapBounds, 0))
            setOnCameraIdleListener(this@VehicleMapFragment)
        }
    }

    override fun onCameraIdle() {
        vehicleViewModel.currentMapBounds = map?.projection?.visibleRegion?.latLngBounds
    }

    private fun addVehicleMarkers(vehicles: List<Vehicle>) {
        map?.clear()
        vehicles.forEach { vehicle -> map?.addMarker(buildMarkerFromVehicle(vehicle)) }
    }

    private fun buildMarkerFromVehicle(vehicle: Vehicle) = MarkerOptions()
            .icon(BitmapDescriptorFactory.fromBitmap(getDrawable(this.context, vehicle.getMapMarkerIcon())?.toBitmap()))
            .position(vehicle.coordinate.getLatLng())
}
