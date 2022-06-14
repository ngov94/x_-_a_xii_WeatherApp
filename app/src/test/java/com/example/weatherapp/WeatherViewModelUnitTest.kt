package com.example.weatherapp

//import com.example.weatherapp.APIResponse.*
import com.example.weatherapp.data.DataBase.PlaceName
import com.example.weatherapp.data.DataBase.WeatherDao
import com.example.weatherapp.data.GeolocationApi.Geolocation
import com.example.weatherapp.data.GeolocationApi.Location
import com.example.weatherapp.data.ReverseGeocoding.Components
import com.example.weatherapp.data.ReverseGeocoding.CurrentCity
import com.example.weatherapp.data.APIResponse.*
import com.example.weatherapp.data.RetroApiInterface
import com.example.weatherapp.data.ReverseGeocoding.Result
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.ui.WeatherViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response.success

@RunWith(JUnit4::class)
class WeatherViewModelUnitTest {
//     lateinit var repo: WeatherRepository
//     lateinit var vm: WeatherViewModel
//
//     @Mock
//     lateinit var inter: RetroApiInterface
//
//     @Mock
//     lateinit var dao: WeatherDao
//
//    @Mock
//    lateinit var fakeAllWeather : AllWeather
//
//     @Before
//     fun setup(){
//         MockitoAnnotations.openMocks(this)
//         repo = WeatherRepository(inter, dao)
//         vm = WeatherViewModel(repo)
//     }
//
//    @Test
//    fun getCurrentWeatherTest(){
//        runBlocking {
//            val fakeWeather = AllWeather((Current(1,2.2,2,2.2,
//                2,2,2,2,2.2,2.2,2,
//                 listOf(Weather("Cloudy", "04d", 1, "cloudy")), 2,
//                 2.2)),
//                 listOf(Daily(2,2.2,2, FeelsLike(2.2,2.2,2.2,2.2),
//                 2,2.2,2,2.2,2,2,Temp(2.2,2.2,2.2,
//                    2.2,2.2,2.2),2.2,listOf(Weather("Cloudy",
//                "04d", 2,"cloudy")),2,2.2,2.2)),
//                listOf(Hourly(2,2.2,2,2.2,2,2.2,2,
//                Rain(2.2),2.2,2.2,2,
//                listOf(Weather("Cloudy", "04d", 1, "cloudy")),2,
//                2.2,2.2)),2.2,2.2,
//                listOf(Minutely(2,2.2)),"PST",12345)
//            Mockito.`when`(repo.getCurrentWeather("0","0","0","0"))
//                .thenReturn(success(fakeWeather))
//
//            vm.getCurrentWeather("0","0","0","0")
//            val result = repo.getCurrentWeather("0","0","0","0")
//
//            assertEquals(fakeWeather,result.body())
//        }
//    }
//
//    @Test
//    fun getGeolocationTest(){
//        runBlocking {
//            val fakeGeolocation = Geolocation(Location(70.0,70.0))
//
//            Mockito.`when`(repo.getGeoloaction("0"))
//                .thenReturn(success(fakeGeolocation))
//
//            vm.getGeoloaction("0")
//            val result = repo.getGeoloaction("0")
//
//            assertEquals(fakeGeolocation,result.body())
//        }
//    }
//
//    @Test
//    fun getCurrentCityTest(){
//        runBlocking {
//            val fakeCurrentCity = CurrentCity(listOf(Result(Components(
//                "LA","USA", "USA", "LA","CA"))))
//
//            Mockito.`when`(repo.getCurrentCity("70, 70","0"))
//                .thenReturn(success(fakeCurrentCity))
//
//            vm.getCurrentCity("70, 70", "0")
//            val result = repo.getCurrentCity("70, 70", "0")
//
//            assertEquals(fakeCurrentCity, result.body())
//        }
//    }
//
//    @Test
//    fun insertWeatherTest(){
//        //
//    }
//
//    @Test
//    fun insertLatLongTest(){
//        //
//    }
//
//    @Test
//    fun getPlaceNameTest(){
//        val fakePlaceName : PlaceName = PlaceName("Los Angeles")
//
//        Mockito.`when`(repo.getPlaceName())
//            .thenReturn(fakePlaceName)
//
//        val result = vm.getPlaceName()
//
//        assertEquals(fakePlaceName, result)
//    }
//
//    @Test
//    fun insertPlaceNameTest(){
//        //
//    }
}