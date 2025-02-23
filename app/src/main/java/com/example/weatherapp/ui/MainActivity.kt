package com.example.weatherapp.ui


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.log.DebugTree
import com.example.weatherapp.ui.currentlocation.CurrentLocationFragment
import com.example.weatherapp.ui.locations.LocationsFragment
import com.example.weatherapp.ui.weekly.WeeklyFragment
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import timber.log.Timber



class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private val fragmentCurrentLocation = CurrentLocationFragment()
    private val fragmentWeekly = WeeklyFragment()
    private val fragmentLocation = LocationsFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = fragmentCurrentLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(DebugTree())

        try {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }catch (e: Exception){
            Timber.e(e)
        }


        try {
            fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, fragmentLocation, "3")
                .hide(fragmentLocation).commit()
            fm.beginTransaction().add(R.id.nav_host_fragment_activity_main, fragmentWeekly, "2")
                .hide(fragmentWeekly).commit()
            fm.beginTransaction()
                .add(R.id.nav_host_fragment_activity_main, fragmentCurrentLocation, "1").commit()
        }catch (e: Exception){
            Timber.e(e)
        }

        val content: View = findViewById(android.R.id.content)

        //Splash Page
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    Thread.sleep(1500)
                    return false
                }
            }
        )



        val navView: BottomNavigationView = binding.bottomNav

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.currentlocationFragment2, R.id.weeklyFragment2, R.id.locationsFragment2
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.currentlocationFragment2 ->{
                    fm.beginTransaction().hide(active).show(fragmentCurrentLocation).commit()
                    active = fragmentCurrentLocation
                    true
                }
                R.id.weeklyFragment2 ->{
                    fm.beginTransaction().hide(active).show(fragmentWeekly).commit()
                    active = fragmentWeekly
                    true
                }
                R.id.locationsFragment2 ->{
                    fm.beginTransaction().hide(active).show(fragmentLocation).commit()
                    active = fragmentLocation
                    true
                }
                else -> false
            }
        }



    }


}


