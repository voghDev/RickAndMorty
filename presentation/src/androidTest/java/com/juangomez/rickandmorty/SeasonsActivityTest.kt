package com.juangomez.rickandmorty

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.juangomez.rickandmorty.views.seasons.SeasonsActivity
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class SeasonsActivityTest : BaseInstrumentationTest() {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SeasonsActivity::class.java, true, false)

    override fun setup() {
        super.setup()
    }

    @Test
    fun showsSeasonsSuccessfully() = runBlockingTest {
        startActivity()
        isImageVisible()
    }

    private fun isImageVisible() {
        onView(withId(R.id.cover_image)).check(matches(isDisplayed()))
    }

    private fun startActivity() {
        val intent = Intent()
        mActivityTestRule.launchActivity(intent)
    }
}