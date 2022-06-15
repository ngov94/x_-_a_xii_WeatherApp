package com.example.weatherapp

import com.example.weatherapp.data.APIResponse.*
import com.example.weatherapp.data.DataBase.FavLocations
import com.example.weatherapp.ui.locations.LocationAdapter
import com.example.weatherapp.ui.weekly.WeeklyAdapter
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LocationAdapterUnitTest {

    lateinit var adapter: LocationAdapter
    lateinit var fakeLocationList: List<LocationWeather>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        fakeLocationList = listOf(
            LocationWeather(
                2.2, 2.2, 2.2, 2.2, "04d",
                FavLocations(null,"LA", "70", "70")
            ))
        adapter = LocationAdapter(fakeLocationList)
    }

    @Test
    fun getItemCount(){
        val result = adapter.getItemCount()

        TestCase.assertEquals(fakeLocationList.size, result)
    }
}