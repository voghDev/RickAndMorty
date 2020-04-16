package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.domain.models.Episodes
import com.juangomez.domain.repositories.EpisodeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetEpisodesUseCaseTest {

    private lateinit var getEpisodesUseCase: GetEpisodesUseCase

    @MockK
    private lateinit var episodesRepository: EpisodeRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to episodes repository when executes use case`() {
        val episodes = mockk<Episodes>()
        getEpisodesUseCase = GetEpisodesUseCase(episodesRepository)

        coEvery { episodesRepository.getEpisodes() } returns Either.Right(episodes)
        runBlocking { getEpisodesUseCase.invoke(this) }
        coVerify(exactly = 1) { episodesRepository.getEpisodes() }
    }
}