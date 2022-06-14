package com.example.weatherapp

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.APIResponse.*
import com.example.weatherapp.data.GeolocationApi.Geolocation
import com.example.weatherapp.data.GeolocationApi.Location
import com.example.weatherapp.data.ReverseGeocoding.Components
import com.example.weatherapp.data.ReverseGeocoding.CurrentCity
import com.example.weatherapp.data.ReverseGeocoding.Result
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response.success
import com.example.weatherapp.data.DataBase.*
import com.example.weatherapp.data.RetroApiInterface
import com.example.weatherapp.data.WeatherRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import junit.framework.TestCase

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
                .thenReturn(Observable.just(fakeAllWeather))

            var result = repo.getCurrentWeather("0","0","0","0")

            result.subscribeBy(
                onNext = {
                    assertEquals(fakeAllWeather, it)
                },
                onError = { println("Error: $it")}
            )
        }
    }

    @Test
    fun getGeolocationTest(){
        runBlocking {
            var fakeGeolocation: Geolocation = Geolocation(Location(70.0, 70.0))
            var fakeApi = "0"
            Mockito.`when`(inter.getGeoloaction(fakeApi))
                .thenReturn(Observable.just(fakeGeolocation))

            var result = repo.getGeoloaction(fakeApi)

            result.subscribeBy(
                onNext = {
                    assertEquals(fakeGeolocation, it)
                },
                onError = { println("Error: $it")}
            )
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
                .thenReturn(Observable.just(fakeCurrentCity))

            var result = repo.getCurrentCity(fakeLatLng,fakeApi)

            result.subscribeBy(
                onNext = {
                    assertEquals(fakeCurrentCity, it)
                },
                onError = { println("Error: $it")}
            )
        }
    }



    @Test
    fun insertLatLong(){
        //
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
        //
    }
}