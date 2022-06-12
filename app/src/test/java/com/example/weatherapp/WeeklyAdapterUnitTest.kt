package com.example.weatherapp

import com.example.weatherapp.APIResponse.Daily
import com.example.weatherapp.APIResponse.FeelsLike
import com.example.weatherapp.APIResponse.Temp
import com.example.weatherapp.APIResponse.Weather
import com.example.weatherapp.ui.weekly.WeeklyAdapter
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class WeeklyAdapterUnitTest {

    lateinit var adapter: WeeklyAdapter
    lateinit var fakeDailyList: List<Daily>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        fakeDailyList = listOf(Daily(1,1.1,1, FeelsLike(1.1,
            1.1,1.1,1.1), 1, 1.1, 1, 1.1, 1,
            1, Temp(1.1,1.1,1.1,1.1,1.1,1.1), 1.1,
            listOf(Weather("Sunny", "04d",1,"sunny")), 2,
            2.2, 2.2
        ))
        adapter = WeeklyAdapter(fakeDailyList)
    }

    @Test
    fun getItemCount(){
        val result = adapter.getItemCount()

        assertEquals(fakeDailyList.size, result)
    }
}