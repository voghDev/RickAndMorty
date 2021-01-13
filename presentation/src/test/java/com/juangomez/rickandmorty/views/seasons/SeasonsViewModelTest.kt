package com.juangomez.rickandmorty.views.seasons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import arrow.core.Either
import com.juangomez.common.Failure
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

    private val useCase = GetSeasonsUseCase(episodeRepository)
    private val viewModel = SeasonsViewModel(useCase)

    @Before
    fun setup() {
        Dispatchers.setMain(coroutineDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun `should show a list of seasons when ViewModel is initialized`() {
        coEvery { episodeRepository.getEpisodes() } returns Either.Right(emptyList())

        viewModel.initialState()

        assertTrue(viewModel.state() is SeasonsViewModel.State.SeasonsLoaded)
    }

    @Test
    fun `should transact to error state when Repository returns an error`() {
        coEvery { episodeRepository.getEpisodes() } returns Either.Left(Failure.ServerError)

        viewModel.initialState()

        assertTrue(viewModel.state() is SeasonsViewModel.State.Error)
    }

    private fun BaseViewModel.state() = this.state.value
}
