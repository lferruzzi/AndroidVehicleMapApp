package br.com.ferruzzi.vehiclemap.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import br.com.ferruzzi.vehiclemap.R
import br.com.ferruzzi.vehiclemap.adapter.VehicleListAdapter
import br.com.ferruzzi.vehiclemap.injection.ApplicationModule.MAIN_ACTIVITY_SCOPE
import br.com.ferruzzi.vehiclemap.model.Vehicle
import br.com.ferruzzi.vehiclemap.viewmodel.VehicleViewModel
import kotlinx.android.synthetic.main.fragment_vehicle_list.progressBar
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject

/**
 * [Fragment] to show a list of [Vehicle]s to the user.
 */
class VehicleListFragment : Fragment() {
    private val vehicleListAdapter: VehicleListAdapter
        by inject(scope = getKoin().getOrCreateScope(MAIN_ACTIVITY_SCOPE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vehicle_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<RecyclerView>(R.id.vehiclesRecyclerView).adapter = vehicleListAdapter
    }

    private fun initializeViewModel() {
        ViewModelProviders.of(this.activity as FragmentActivity).get(VehicleViewModel::class.java)
            .let { vehicleViewModel ->

                vehicleViewModel.vehicles.observe(this.activity as FragmentActivity, Observer<List<Vehicle>> {
                    vehicleListAdapter.vehicles = it
                })

                vehicleViewModel.isRequestingNewVehicles.observe(this.activity as FragmentActivity, Observer<Boolean> {
                    progressBar.visibility = if (it) VISIBLE else GONE
                })
            }
    }
}
