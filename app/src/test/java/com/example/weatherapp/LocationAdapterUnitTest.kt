package com.example.weatherapp

import com.example.weatherapp.data.APIResponse.*
import com.example.weatherapp.ui.locations.LocationAdapter
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class LocationAdapterUnitTest {

//    lateinit var adapter: LocationAdapter
//    lateinit var fakeLocationList: List<LocationWeather>
//
//    @Before
//    fun setup(){
//        MockitoAnnotations.openMocks(this)
//        fakeLocationList = listOf(
//            LocationWeather(AllWeather((Current(1,2.2,2,2.2,
//                2,2,2,2,2.2,2.2,2,
//                listOf(Weather("Cloudy", "04d", 1, "cloudy")), 2,
//                2.2)),
//                listOf(Daily(2,2.2,2, FeelsLike(2.2,2.2,2.2,2.2),
//                    2,2.2,2,2.2,2,2,Temp(2.2,2.2,2.2,
//                        2.2,2.2,2.2),2.2,listOf(Weather("Cloudy",
//                        "04d", 2,"cloudy")),2,2.2,2.2)),
//                listOf(Hourly(2,2.2,2,2.2,2,2.2,2,
//                    Rain(2.2),2.2,2.2,2,
//                    listOf(Weather("Cloudy", "04d", 1, "cloudy")),2,
//                    2.2,2.2)),2.2,2.2,
//                listOf(Minutely(2,2.2)),"PST",12345), "LA")
//        )
//        adapter = LocationAdapter(fakeLocationList)
//    }
//
//    @Test
//    fun getItemCount(){
//        val result = adapter.getItemCount()
//
//        TestCase.assertEquals(fakeLocationList.size, result)
//    }
}