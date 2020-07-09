package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.domain.models.Episode
import com.juangomez.domain.models.Season
import com.juangomez.domain.models.groupBySeasons
import com.juangomez.domain.repositories.EpisodeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetSeasonsUseCaseTest {

    private lateinit var getSeasonsUseCase: GetSeasonsUseCase

    @MockK
    private lateinit var episodeRepository: EpisodeRepository

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to episode repository when executes use case`() {
        val episodes = mockk<List<Episode>>(relaxed = true)
        getSeasonsUseCase = GetSeasonsUseCase(episodeRepository)

        coEvery { episodeRepository.getEpisodes() } returns Either.Right(episodes)
        runBlocking { getSeasonsUseCase.invoke(this, BaseUseCase.None()) }
        coVerify(exactly = 1) { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodes.groupBySeasons() }
    }
}