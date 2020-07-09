package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.EpisodeCacheProvider
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class EpisodeRepositoryTest {

    private lateinit var episodeRepository: EpisodeRepository

    @MockK
    private lateinit var episodeRemoteProvider: EpisodeRemoteProvider

    @MockK
    private lateinit var episodeCacheProvider: EpisodeCacheProvider

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to remote and cache providers when get episodes and get a successful response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteProvider, episodeCacheProvider)

        coEvery { episodeRemoteProvider.getEpisodes() } returns Either.Right(episodes)
        coEvery { episodeCacheProvider.setEpisodes(episodes) } returns Unit
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodeRemoteProvider.getEpisodes() }
        coVerify(exactly = 1) { episodeCacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to remote provider when get episodes and get a failed response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteProvider, episodeCacheProvider)

        coEvery { episodeRemoteProvider.getEpisodes() } returns Either.Left(Failure.ServerError)
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodeRemoteProvider.getEpisodes() }
        coVerify(exactly = 0) { episodeCacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to cache provider when get episode by id and return a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteProvider, episodeCacheProvider)

        coEvery { episodeCacheProvider.getEpisodeById(episodeId) } returns episode
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeRemoteProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeCacheProvider.setEpisode(episode) }
    }

    @Test
    fun `should call to cache and remote providers when get episode and get a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteProvider, episodeCacheProvider)

        coEvery { episodeCacheProvider.getEpisodeById(episodeId) } returns null
        coEvery { episodeRemoteProvider.getEpisodeById(episodeId) } returns Either.Right(episode)
        coEvery { episodeCacheProvider.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeRemoteProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeCacheProvider.setEpisode(episode) }
    }

    @Test
    fun `should call to remote and cache providers when get episode and get a failed response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteProvider, episodeCacheProvider)

        coEvery { episodeCacheProvider.getEpisodeById(episodeId) } returns null
        coEvery { episodeRemoteProvider.getEpisodeById(episodeId) } returns Either.Left(Failure.ServerError)
        coEvery { episodeCacheProvider.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeRemoteProvider.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeCacheProvider.setEpisode(episode) }
    }
}