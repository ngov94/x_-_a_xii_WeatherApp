package com.example.weatherapp

import com.example.weatherapp.APIResponse.AllWeather
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
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class RetroApiInterfaceUnitTest {

    lateinit var inter: RetroApiInterface
    lateinit var mockServer: MockWebServer
    lateinit var gson : Gson

    @Mock
    lateinit var fakeAllWeather: AllWeather

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
        mockServer = MockWebServer()
        gson = Gson()
        inter = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RetroApiInterface::class.java)
    }

    @After
    fun destroy() {
        mockServer.shutdown()
    }

    @Test
    fun getCurrentWeatherTest(){
        runBlocking {
            var mockRes = MockResponse()

            mockServer.enqueue(mockRes.setBody("[]"))

            val res = inter.getCurrentWeather("0","0","0","0")
            val req = mockServer.takeRequest()

            assertEquals(mockRes, req.path)
        }
    }

    @Test
    fun getGeolocationTest(){
        runBlocking {
            var mockRes = MockResponse().setBody("[{'id':1}]")

            mockServer.enqueue(mockRes)

            val res = inter.getGeoloaction("0").body()
            val req = mockServer.takeRequest()

            assertEquals(true ,res != null)
        }
    }

    @Test
    fun getCurrentCityTest(){
        runBlocking {
            var mockRes = MockResponse()

            mockServer.enqueue(mockRes.setBody("[]"))

            val res = inter.getCurrentCity("0","0")
            val req = mockServer.takeRequest()

            assertEquals(mockRes, req.path)
        }
    }

}