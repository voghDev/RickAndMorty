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
}
