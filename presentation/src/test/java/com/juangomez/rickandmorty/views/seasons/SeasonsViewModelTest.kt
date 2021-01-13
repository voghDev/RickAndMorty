package com.juangomez.rickandmorty.views.seasons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.juangomez.domain.repositories.EpisodeRepository
import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.views.base.BaseViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SeasonsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val coroutineDispatcher = TestCoroutineDispatcher()

    private val episodeRepository = mockk<EpisodeRepository>()

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `should show a list of seasons when ViewModel is initialized`() {
        coEvery { episodeRepository.getEpisodes() } returns Either.Right(emptyList())

        val useCase = GetSeasonsUseCase(episodeRepository)
        val viewModel = SeasonsViewModel(useCase)

        viewModel.initialState()

        assertTrue(viewModel.state() is SeasonsViewModel.State.SeasonsLoaded)
    }

    private fun BaseViewModel.state() = this.state.value
}
