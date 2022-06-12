package com.example.weatherapp

import androidx.lifecycle.LiveData
import com.example.weatherapp.APIResponse.*
import com.example.weatherapp.GeolocationApi.Geolocation
import com.example.weatherapp.GeolocationApi.Location
import com.example.weatherapp.ReverseGeocoding.Components
import com.example.weatherapp.ReverseGeocoding.CurrentCity
import com.example.weatherapp.ReverseGeocoding.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response.success
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapp.DataBase.*
import junit.framework.TestCase
import org.junit.After

@RunWith(JUnit4::class)
class WeatherRepositoryUnitTest : TestCase() {

    lateinit var repo: WeatherRepository

    @Mock
    lateinit var inter: RetroApiInterface

    @Mock
    lateinit var dao: WeatherDao

    @Mock
    lateinit var fakeAllWeather : AllWeather

    @Mock
    lateinit var fakeAllWeatherEntity: AllWeatherEntity

    @Mock
    lateinit var fakeLiveAllWeatherEntity : LiveData<AllWeatherEntity>

    @Mock
    lateinit var fakeLiveCityLatLong: LiveData<CityLatLong>

    @Mock
    lateinit var fakeLiveFavLocations: LiveData<List<FavLocations>>

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        repo = WeatherRepository(inter, dao)

    }

    @Test
    fun getCurrentWeatherTest(){
        runBlocking {
            Mockito.`when`(inter.getCurrentWeather("0","0","0","0"))
                .thenReturn(success(fakeAllWeather))

            var response = repo.getCurrentWeather("0","0","0","0")

            assertEquals(fakeAllWeather,response.body())
        }
    }

    @Test
    fun getGeolocationTest(){
        runBlocking {
            var fakeGeolocation: Geolocation = Geolocation(Location(70.0, 70.0))
            var fakeApi = "0"
            Mockito.`when`(inter.getGeoloaction(fakeApi))
                .thenReturn(success(fakeGeolocation))

            var response = repo.getGeoloaction(fakeApi)

            assertEquals(fakeGeolocation,response.body())
        }
    }

    @Test
    fun getCurrentCityTest(){
        runBlocking {
            var fakeCurrentCity: CurrentCity = CurrentCity(listOf<Result>(Result(
                Components("LA", "USA","USA","LA", "CA"))))
            var fakeLatLng = "Place"
            var fakeApi = "0"

            Mockito.`when`(inter.getCurrentCity(fakeLatLng,fakeApi))
                .thenReturn(success(fakeCurrentCity))

            var response = repo.getCurrentCity(fakeLatLng,fakeApi)

            assertEquals(fakeCurrentCity,response.body())
        }
    }

    @Test
    fun getAllWeatherTest(){
        Mockito.`when`(dao.getAllWeather())
            .thenReturn(fakeLiveAllWeatherEntity)

        var result = repo.getAllWeather()

        assertEquals(fakeLiveAllWeatherEntity,result)

    }

    @Test
    fun insertWeatherTest(){
        var fakeWeather: AllWeatherEntity = AllWeatherEntity("33","34",
        "16","12/12/20","36","Sunny","5am","5pm",
        "34%","130kph")

        repo.insertWeather(fakeWeather)

        val weatherEntities = repo.getAllWeather()
        //Testing insert into Mocked db how do I test
        //assertEquals(weatherEntities,fakeWeather)
    }

    @Test
    fun getLatLongTest() {

        Mockito.`when`(dao.getLatLong())
            .thenReturn(fakeLiveCityLatLong)

        var result = repo.getLatLong()

        assertEquals(fakeLiveCityLatLong, result)
    }

    @Test
    fun insertLatLong(){
        //
    }

    @Test
    fun getPlaceNameTest(){
        var fakePlace : PlaceName = PlaceName("LA")
        Mockito.`when`(dao.getPlaceName())
            .thenReturn(fakePlace)

        var result = repo.getPlaceName()

        assertEquals(fakePlace, result)
    }

    @Test
    fun insertPlaceNameTest(){
        //
    }

    @Test
    fun getFavLocationsListTest(){
        Mockito.`when`(dao.getFavLocationsList())
            .thenReturn(fakeLiveFavLocations)

        var result = repo.getFavLocationsList()

        assertEquals(fakeLiveFavLocations, result)

    }

    @Test
    fun insertFavLocation(){
        
    }
}