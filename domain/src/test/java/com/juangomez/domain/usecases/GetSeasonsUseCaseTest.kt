package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.domain.models.Season
import com.juangomez.domain.repositories.SeasonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonsUseCase: GetSeasonsUseCase

    @MockK
    private lateinit var seasonRepository: SeasonRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to seasons repository when executes use case`() {
        val seasons = mockk<List<Season>>()
        getSeasonsUseCase = GetSeasonsUseCase(seasonRepository)

        coEvery { seasonRepository.getSeasons() } returns Either.Right(seasons)
        runBlocking { getSeasonsUseCase.invoke(this) }
        coVerify(exactly = 1) { seasonRepository.getSeasons() }
    }
}