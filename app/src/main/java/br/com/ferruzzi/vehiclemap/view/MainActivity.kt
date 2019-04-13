package br.com.ferruzzi.vehiclemap.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import br.com.ferruzzi.vehiclemap.R
import br.com.ferruzzi.vehiclemap.injection.ApplicationModule.MAIN_ACTIVITY_SCOPE
import br.com.ferruzzi.vehiclemap.model.Vehicle
import br.com.ferruzzi.vehiclemap.viewmodel.VehicleViewModel
import org.koin.android.ext.android.getKoin
import org.koin.core.scope.Scope

/**
 * Main activity of the application, used to present the [Vehicle]s information listed on [VehicleListFragment]
 * and their position on [VehicleMapFragment].
 */
class MainActivity : AppCompatActivity() {

    private lateinit var vehicleViewModel: VehicleViewModel
    private lateinit var mainActivityScope: Scope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityScope = getKoin().getOrCreateScope(MAIN_ACTIVITY_SCOPE)
        setContentView(R.layout.activity_main)
        initializeViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityScope.close()
    }

    private fun initializeViewModel() {
        vehicleViewModel = ViewModelProviders.of(this).get(VehicleViewModel::class.java)

        vehicleViewModel.wasLastRequestSuccessful.observe(this, Observer<Boolean> {
            if (!it) showErrorMessage()
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(this, R.string.requestErrorMessage, Toast.LENGTH_LONG).show()
    }
}
