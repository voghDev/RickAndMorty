package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.domain.models.Episodes
import com.juangomez.domain.models.Seasons
import com.juangomez.domain.repositories.SeasonsRepository
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
    private lateinit var seasonsRepository: SeasonsRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to seasons repository when executes use case`() {
        val seasons = mockk<Seasons>()
        getSeasonsUseCase = GetSeasonsUseCase(seasonsRepository)

        coEvery { seasonsRepository.getSeasons() } returns Either.Right(seasons)
        runBlocking { getSeasonsUseCase.invoke(this) }
        coVerify(exactly = 1) { seasonsRepository.getSeasons() }
    }
}