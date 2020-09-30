package com.juangomez.rickandmorty.views.seasons

import com.juangomez.domain.usecases.GetSeasonsUseCase
import com.juangomez.rickandmorty.MockEpisodeRepository
import junit.framework.Assert.assertNotNull
import org.junit.Test

class SeasonsViewModelTest {
    private val mockUseCase = GetSeasonsUseCase(MockEpisodeRepository())

    @Test
    fun `view model should be testable`() {
        val viewModel = SeasonsViewModel(mockUseCase)

        viewModel.initialState()

        assertNotNull(viewModel)
    }
}