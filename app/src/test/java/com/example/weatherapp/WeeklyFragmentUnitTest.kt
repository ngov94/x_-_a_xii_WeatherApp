package com.example.weatherapp

import com.example.weatherapp.ui.weekly.WeeklyFragment
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import androidx.fragment.app.testing.*
import androidx.lifecycle.Lifecycle

@RunWith(JUnit4::class)
class WeeklyFragmentUnitTest {

    lateinit var scenario: FragmentScenario<WeeklyFragment>

    @Before
    fun setup(){
        scenario = launchFragmentInContainer(
            fragmentArgs = null,
            themeResId = R.style.Theme_WeatherApp,
            initialState = Lifecycle.State.RESUMED
        )

    }

}