package com.example.weatherapp


import com.example.weatherapp.APIResponse.AllWeather
import com.example.weatherapp.DataBase.FavLocations
import com.example.weatherapp.DataBase.WeatherDao
import com.example.weatherapp.ui.locations.LocationViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class LocationViewModelUnitTest {
    lateinit var repo: WeatherRepository
    lateinit var vm: LocationViewModel

    @Mock
    lateinit var inter: RetroApiInterface

    @Mock
    lateinit var dao: WeatherDao

    @Mock
    lateinit var fakeAllWeather : AllWeather

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repo = WeatherRepository(inter, dao)
        vm = LocationViewModel(repo)
    }

    @Test
    fun insertFavLocationTest(){
        //
    }

//    @Test
//    fun getFavLocationWeatherListTest(){
//        runBlocking {
//            val fakeListFavLocation = listOf(FavLocations("LA","40","70"))
//
//            Mockito.`when`(repo.getFavLocationsList())
//                .thenReturn(fakeListFavLocation)
//
//            vm.getFavLocationWeatherList(fakeListFavLocation, "0","0")
//            val result = repo.getFavLocationsList()
//
//            assertEquals(fakeListFavLocation, result)
//        }
//    }
}