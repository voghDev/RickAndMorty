package com.juangomez.rickandmorty

import androidx.test.espresso.intent.Intents
import org.junit.After
import org.junit.Before
import org.koin.test.KoinTest

open class BaseInstrumentationTest : KoinTest {

    @Before
    open fun setup() {
        Intents.init()
    }

    @After
    open fun tearDown() {
        Intents.release()
    }

    fun w8(seconds: Long) = Thread.sleep(seconds)

    companion object {
        private const val FINGERPRINT = "INSTRUMENTATION_TEST_DEMO_FINGERPRINT"
        const val LOGOUT_SERVICE_DELAY = 1500L
        const val LOGIN_SERVICE_DELAY = 2500L
    }
}
