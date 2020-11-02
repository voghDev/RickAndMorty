package com.juangomez.data.repositories

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.EpisodeCacheDataSource
import com.juangomez.data.providers.remote.EpisodeRemoteDataSource
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
    private lateinit var episodeRemoteDataSource: EpisodeRemoteDataSource

    @MockK
    private lateinit var episodeCacheDataSource: EpisodeCacheDataSource

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to remote and cache providers when get episodes and get a successful response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteDataSource, episodeCacheDataSource)

        coEvery { episodeRemoteDataSource.getEpisodes() } returns Either.Right(episodes)
        coEvery { episodeCacheDataSource.setEpisodes(episodes) } returns Unit
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodeRemoteDataSource.getEpisodes() }
        coVerify(exactly = 1) { episodeCacheDataSource.setEpisodes(episodes) }
    }

    @Test
    fun `should call to remote provider when get episodes and get a failed response`() {
        val episodes = mockk<List<Episode>>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteDataSource, episodeCacheDataSource)

        coEvery { episodeRemoteDataSource.getEpisodes() } returns Either.Left(Failure.ServerError)
        runBlocking { episodeRepository.getEpisodes() }
        coVerify(exactly = 1) { episodeRemoteDataSource.getEpisodes() }
        coVerify(exactly = 0) { episodeCacheDataSource.setEpisodes(episodes) }
    }

    @Test
    fun `should call to cache provider when get episode by id and return a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteDataSource, episodeCacheDataSource)

        coEvery { episodeCacheDataSource.getEpisodeById(episodeId) } returns episode
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeRemoteDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeCacheDataSource.setEpisode(episode) }
    }

    @Test
    fun `should call to cache and remote providers when get episode and get a valid response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteDataSource, episodeCacheDataSource)

        coEvery { episodeCacheDataSource.getEpisodeById(episodeId) } returns null
        coEvery { episodeRemoteDataSource.getEpisodeById(episodeId) } returns Either.Right(episode)
        coEvery { episodeCacheDataSource.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeRemoteDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeCacheDataSource.setEpisode(episode) }
    }

    @Test
    fun `should call to remote and cache providers when get episode and get a failed response`() {
        val episodeId = 1
        val episode = mockk<Episode>()

        episodeRepository = EpisodeRepositoryImpl(episodeRemoteDataSource, episodeCacheDataSource)

        coEvery { episodeCacheDataSource.getEpisodeById(episodeId) } returns null
        coEvery { episodeRemoteDataSource.getEpisodeById(episodeId) } returns Either.Left(Failure.ServerError)
        coEvery { episodeCacheDataSource.setEpisode(episode) } returns Unit
        runBlocking { episodeRepository.getEpisode(episodeId) }
        coVerify(exactly = 1) { episodeCacheDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 1) { episodeRemoteDataSource.getEpisodeById(episodeId) }
        coVerify(exactly = 0) { episodeCacheDataSource.setEpisode(episode) }
    }
}