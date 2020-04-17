package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.CacheProvider
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class EpisodeRepositoryTest {

    private lateinit var episodeRepository: EpisodeRepository

    @MockK
    private lateinit var episodesRemoteProvider: EpisodesRemoteProvider

    @MockK
    private lateinit var cacheProvider: CacheProvider

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to remote and cache providers when get episodes and get a successful response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { episodesRemoteProvider.getEpisodes() } returns Either.Right(episodes)
        coEvery { cacheProvider.setEpisodes(episodes) } returns Unit
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisodes() }
        coVerify(exactly = 1) { cacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to remote provider when get episodes and get a failed response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { episodesRemoteProvider.getEpisodes() } returns Either.Left(Failure.ServerError)
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisodes() }
        coVerify(exactly = 0) { cacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to cache provider when get episode by id and return a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { cacheProvider.getEpisode(episodeId) } returns episode
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { cacheProvider.getEpisode(episodeId) }
        coVerify(exactly = 0) { episodesRemoteProvider.getEpisode(episodeId) }
        coVerify(exactly = 0) { cacheProvider.setEpisode(episode) }
    }

    @Test
    fun `should call to cache and remote providers when get episode and get a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { cacheProvider.getEpisode(episodeId) } returns null
        coEvery { episodesRemoteProvider.getEpisode(episodeId) } returns Either.Right(episode)
        coEvery { cacheProvider.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { cacheProvider.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisode(episodeId) }
        coVerify(exactly = 1) { cacheProvider.setEpisode(episode) }
    }

    @Test
    fun `should call to remote and cache providers when get episode and get a failed response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodesRemoteProvider, cacheProvider)

        coEvery { cacheProvider.getEpisode(episodeId) } returns null
        coEvery { episodesRemoteProvider.getEpisode(episodeId) } returns Either.Left(Failure.ServerError)
        coEvery { cacheProvider.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { cacheProvider.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodesRemoteProvider.getEpisode(episodeId) }
        coVerify(exactly = 0) { cacheProvider.setEpisode(episode) }
    }
}