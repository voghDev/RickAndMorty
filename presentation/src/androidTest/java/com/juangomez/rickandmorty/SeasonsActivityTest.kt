package com.juangomez.rickandmorty

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.views.seasons.SeasonsActivity
import com.juangomez.rickandmorty.views.seasons.SeasonsViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@LargeTest
@RunWith(AndroidJUnit4::class)
class SeasonsActivityTest : BaseInstrumentationTest() {

    private val testModule = module {
        viewModel {
            SeasonsViewModel(GetSeasonsUseCase(MockEpisodeRepository()))
        }
    }

    override fun setup() {
        super.setup()

        stopKoin()
        startKoin { modules(testModule) }
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SeasonsActivity::class.java, true, false)

    @Test
    fun showsSeason2EpisodeSuccessfully() {
        startActivity()
        onView(withId(R.id.season_number_text_selector)).perform(click())
        onView(withText("Season 2")).perform(click());

        onView(withText("Bjorn slippy")).check(matches(isDisplayed()))
    }

    @Test
    fun showsSeason1EpisodeSuccessfully() {
        startActivity()
        onView(withId(R.id.season_number_text_selector)).perform(click())
        onView(withText("Season 1")).perform(click());

        onView(withText("A day at the races")).check(matches(isDisplayed()))
    }

    private fun startActivity() {
        val intent = Intent()
        mActivityTestRule.launchActivity(intent)
    }
}