package com.example.weatherapp

import com.example.weatherapp.data.RetroApiInterface
import com.google.gson.Gson
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RetroApiInterfaceUnitTest {

//    lateinit var inter: RetroApiInterface
//    lateinit var mockServer: MockWebServer
//    lateinit var gson : Gson
//
//
//
//    @Before
//    fun setup(){
//        MockitoAnnotations.openMocks(this)
//        mockServer = MockWebServer()
//        gson = Gson()
//        inter = Retrofit.Builder()
//            .baseUrl(mockServer.url(""))
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create(RetroApiInterface::class.java)
//    }
//    //Gradle Is hanging with these tests
//    @Test
//    fun getCurrentWeatherTest(){
//        runBlocking {
//            var mockRes = MockResponse().setBody("Hello")
//
//            mockServer.enqueue(mockRes)
//
//            val res = inter.getCurrentWeather("0","0","0","0")
//            val req = mockServer.takeRequest()
//
//            assertEquals("onecal?lat=0&lon=0&appid=0&units=0", req.path)
//        }
//    }
//
//    @Test
//    fun getGeolocationTest(){
//        runBlocking {
//            var mockRes = MockResponse().setBody("Hello")
//
//            mockServer.enqueue(mockRes)
//
//            val res = inter.getGeoloaction("0")
//            val req = mockServer.takeRequest()
//
//            assertEquals(true ,res.body() != null)
//            assertEquals("https://www.googleapis.com/geolocation/v1/geolocate?key=0",
//                req.path)
//        }
//    }
//
//    @Test
//    fun getCurrentCityTest(){
//        runBlocking {
//            var mockRes = MockResponse().setBody("Hello")
//
//            mockServer.enqueue(mockRes)
//
//            val res = inter.getCurrentCity("0","0")
//            val req = mockServer.takeRequest()
//
//            assertEquals(true, res.body() != null)
//            assertEquals("https://api.opencagedata.com/geocode/v1/json?q=0&key=0",
//                req.path)
//        }
//    }
//
//    @After
//    fun destroy() {
//        mockServer.shutdown()
//    }
}