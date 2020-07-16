package com.juangomez.rickandmorty

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.juangomez.domain.usecases.GetCharactersUseCase
import com.juangomez.domain.usecases.GetEpisodeUseCase
import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.views.seasons.SeasonsActivity
import com.juangomez.rickandmorty.views.seasons.SeasonsViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.dsl.module
import org.koin.test.KoinTest

@LargeTest
@RunWith(AndroidJUnit4::class)
class SeasonsActivityTest : BaseInstrumentationTest() {
    private val testModule = module {
        factory { GetSeasonsMockUseCase() }

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
    fun showsSeasonImageSuccessfully() {
        startActivity()
        isImageVisible()
    }

    @Test
    fun showsSeasonTextSuccessfully() {
        startActivity()
        isTextVisible()
    }

    private fun isImageVisible() {
        onView(withId(R.id.cover_image)).check(matches(isDisplayed()))
    }

    private fun isTextVisible() {
        onView(withId(R.id.summary_text)).check(matches(isDisplayed()))
    }

    private fun startActivity() {
        val intent = Intent()
        mActivityTestRule.launchActivity(intent)
    }
}