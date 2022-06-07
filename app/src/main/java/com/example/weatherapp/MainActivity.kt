package com.example.weatherapp


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

//    lateinit var vm : WeatherViewModel

    //---------------
    private lateinit var binding: ActivityMainBinding
//--------------
    companion object {
    const val PERMISSION_LOCATION_REQUEST_CODE = 1
}
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        vm = WeatherViewModel()

//        var latitude = "40.71427"
//        var longitude = "-74.00597"
//        var apiKey = "2969a813a03aab47497e1da3a8cf1a81"
//        var String = "metric"  //imperial
//
//        //Instantiate the ProviderClient
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//
//        //If has location permissions
//        if(hasLocationPermission()) {
//            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//                Log.d("Testing", location.latitude.toString())
//                Log.d("Testing", location.longitude.toString())
//                //change lat and long values
//                latitude = location.latitude.toString()
//                longitude = location.longitude.toString()
//                //Update weather with new location
//                vm.getCurrentWeather(latitude,longitude,apiKey,String)
//            }
//        } else {
//            //If it doesn't have location permission, request it.
//            requestLocalPermission()
//        }
//
//        vm.currentWeather.observe(this) {
//            val gson = GsonBuilder().setPrettyPrinting().create()
//            val pJson = gson.toJson(it)
//            println(pJson)
//
//            tv_city_name.text = it.name
//            tv_date_and_time.text = SimpleDateFormat("dd/M/yyyy hh:mm a").format(Date())
//            tv_day_max_temp.text = "Day " + it.main.tempMax.toString()+"º"
//            tv_day_min_temp.text = "Night " +it.main.tempMin.toString()+"º"
//            tv_current_temp.text = it.main.temp.toString()+"º"
//            tv_feels_like.text = "Feels like "+ it.main.feelsLike.toString()+"º"
//            tv_weather_type.text = it.weather[0].description.capitalize()
//        }
//
//
//        vm.getCurrentWeather(latitude,longitude,apiKey,String)

//----------------------------
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.locationsFragment2, R.id.weeklyFragment2, R.id.mapFragment2
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//--------------------------
    }

    //Checks for permission
    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    //requests permission with reason why it is being asked
    private fun requestLocalPermission(){
        EasyPermissions.requestPermissions(
            this,
            "This application uses location permission to give local weather",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    //Have EasyPermissions Library take care of the results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //if the permission is permanently disabled bring up the settings dialog to change if needed
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            //else request the permission
            requestLocalPermission()
        }
    }

    //Toast if permission is granted
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Toast.makeText(
            this,
            "Permission Granted!",
            Toast.LENGTH_SHORT
        ).show()
    }

}