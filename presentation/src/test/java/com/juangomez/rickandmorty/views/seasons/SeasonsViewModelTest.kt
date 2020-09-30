package com.juangomez.rickandmorty.views.seasons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juangomez.domain.usecases.GetSeasonsUseCase
import io.mockk.MockKAnnotations
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SeasonsViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mockUseCase = mockk<GetSeasonsUseCase>()

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should transact to Loading state when ViewModel is initialized`() {
        val viewModel = SeasonsViewModel(mockUseCase)

        viewModel.initialState()

        assertTrue(viewModel.state.value is SeasonsViewModel.State.Loading)
    }
}