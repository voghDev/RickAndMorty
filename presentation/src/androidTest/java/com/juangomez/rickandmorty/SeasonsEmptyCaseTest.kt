package com.juangomez.rickandmorty

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository
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
class SeasonsEmptyCaseTest : BaseInstrumentationTest() {

    private val emptyEpisodeRepository = object : EpisodeRepository {
        override suspend fun getEpisodes(): Either<Failure, List<Episode>> =
            Either.Right(emptyList())

        override suspend fun getEpisode(id: Int): Either<Failure, Episode> =
            Either.Left(Failure.ServerError)
    }

    private val testModule = module {
        factory { GetSeasonsUseCase(emptyEpisodeRepository) }

        viewModel {
            SeasonsViewModel(get())
        }
    }

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(SeasonsActivity::class.java, true, false)

    @Test
    fun showsSeasonImageSuccessfully() {
        stopKoin()
        startKoin { modules(testModule) }

        startActivity()
        isImageVisible()
    }

    @Test
    fun showsSeasonTextSuccessfully() {
        stopKoin()
        startKoin { modules(testModule) }

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