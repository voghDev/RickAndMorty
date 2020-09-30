package com.juangomez.rickandmorty.views.seasons

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juangomez.domain.usecases.GetSeasonsUseCase
import io.mockk.MockKAnnotations
import io.mockk.mockk
import junit.framework.Assert.assertNotNull
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
    fun `should be able to init a view model without errors`() {
        val viewModel = SeasonsViewModel(mockUseCase)

        viewModel.initialState()

        assertNotNull(viewModel)
    }
}