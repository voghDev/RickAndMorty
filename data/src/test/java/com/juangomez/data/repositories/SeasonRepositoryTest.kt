package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.CacheProvider
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.models.Season
import com.juangomez.domain.models.groupBySeasons
import com.juangomez.domain.repositories.SeasonRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SeasonRepositoryTest {

    private lateinit var seasonRepository: SeasonRepository

    @MockK
    private lateinit var episodesRemoteProvider: EpisodesRemoteProvider

    @MockK
    private lateinit var cacheProvider: CacheProvider

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to remote and cache providers when get episodes and get a successful response`() {
        val episodes = mockk<List<Episode>>(relaxed = true)

        seasonRepository = SeasonRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { episodesRemoteProvider.getEpisodes() } returns Either.Right(episodes)
        coEvery { cacheProvider.setEpisodes(episodes) } returns Unit
        runBlocking { seasonRepository.getSeasons() }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisodes() }
        coVerify(exactly = 1) { cacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to remote provider when get episodes and get a failed response`() {
        val episodes = mockk<List<Episode>>()

        seasonRepository = SeasonRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { episodesRemoteProvider.getEpisodes() } returns Either.Left(Failure.ServerError)
        runBlocking { seasonRepository.getSeasons() }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisodes() }
        coVerify(exactly = 0) { cacheProvider.setEpisodes(episodes) }
    }
}