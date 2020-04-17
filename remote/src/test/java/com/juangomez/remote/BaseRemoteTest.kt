package com.juangomez.remote

import com.google.gson.Gson
import io.mockk.MockKAnnotations
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.converter.gson.GsonConverterFactory

open class BaseRemoteTest {

    companion object {
        private const val PORT = 8080
        const val BASE_URL = "http://localhost:$PORT/"
    }

    enum class Status(val code: Int) {
        OK(200),
        INTERNAL_SERVER_ERROR(500)
    }

    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        mockWebServer = MockWebServer()
        mockWebServer.start(PORT)
        mockWebServer.url(BASE_URL)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    fun provideGsonConverterFactory() = GsonConverterFactory.create(Gson())

    fun provideInterceptors() = arrayOf(provideLoggingInterceptor())

    private fun provideLoggingInterceptor() = HttpLoggingInterceptor() as Interceptor
}

