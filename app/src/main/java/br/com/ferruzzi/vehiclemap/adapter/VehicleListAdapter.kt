package br.com.ferruzzi.vehiclemap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import br.com.ferruzzi.vehiclemap.R
import br.com.ferruzzi.vehiclemap.adapter.VehicleListAdapter.VehicleViewHolder
import br.com.ferruzzi.vehiclemap.extension.getIcon
import br.com.ferruzzi.vehiclemap.extension.getLatLng
import br.com.ferruzzi.vehiclemap.model.Vehicle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.vehicle_list_item.view.fleetType
import kotlinx.android.synthetic.main.vehicle_list_item.view.vehicleId
import kotlinx.android.synthetic.main.vehicle_list_item.view.vehicleTypeImage
import kotlinx.android.synthetic.main.vehicle_list_item.view.vehicleAddress
import kotlin.properties.Delegates.observable

/**
 * [Adapter] to present [vehicles] on a [RecyclerView].
 */
class VehicleListAdapter : Adapter<VehicleViewHolder>() {
    /**
     * [Vehicle] collection to be shown.
     */
    var vehicles by observable(initialValue = listOf<Vehicle>(), onChange = { _, _, _ -> notifyDataSetChanged() })

    /**
     * [GoogleMap] reference to zoom in after an item being clicked.
     */
    var map: GoogleMap? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_item, parent, false)
        return VehicleViewHolder(itemView)
    }

    override fun getItemCount() = vehicles.size

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle: Vehicle = vehicles[position]

        holder.apply {
            vehicleId.text = vehicle.id.toString()
            vehicleTypeImage.setImageResource(vehicle.getIcon())
            fleetType.text = vehicle.fleetType
            itemView.setOnClickListener { onItemClicked(position) }
            vehicleAddress.text = vehicle.address?.getAddressLine(0) ?: ""
        }
    }

    private fun onItemClicked(position: Int) {
        val vehicle = vehicles[position]
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(vehicle.coordinate.getLatLng(), DEFAULT_ZOOM))
    }

    inner class VehicleViewHolder(itemView: View) : ViewHolder(itemView) {
        val vehicleId: TextView = itemView.vehicleId
        val fleetType: TextView = itemView.fleetType
        val vehicleTypeImage: ImageView = itemView.vehicleTypeImage
        val vehicleAddress: TextView = itemView.vehicleAddress
    }

    companion object {
        private const val DEFAULT_ZOOM = 13f
    }
}
