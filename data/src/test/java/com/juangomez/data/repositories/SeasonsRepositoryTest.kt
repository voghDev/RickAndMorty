package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.CacheProvider
import com.juangomez.data.providers.RemoteProvider
import com.juangomez.data.repositories.SeasonsRepositoryImpl
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

class SeasonsRepositoryTest {

    private lateinit var seasonsRepository: SeasonsRepository

    @MockK
    private lateinit var remoteProvider: RemoteProvider

    @MockK
    private lateinit var cacheProvider: CacheProvider

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to remote and cache providers when get episodes and get a successful response`() {
        val episodes = mockk<Episodes>()
        val seasons = mockk<Seasons>()

        seasonsRepository = SeasonsRepositoryImpl(remoteProvider, cacheProvider)

        coEvery { remoteProvider.getEpisodes() } returns Either.Right(episodes)
        coEvery { cacheProvider.setEpisodes(episodes) } returns Unit
        coEvery { episodes.groupBySeasons() } returns seasons
        runBlocking { seasonsRepository.getSeasons() }
        coVerify(exactly = 1) { remoteProvider.getEpisodes() }
        coVerify(exactly = 1) { cacheProvider.setEpisodes(episodes) }
    }

    @Test
    fun `should call to remote provider when get episodes and get a failed response`() {
        val episodes = mockk<Episodes>()

        seasonsRepository = SeasonsRepositoryImpl(remoteProvider, cacheProvider)

        coEvery { remoteProvider.getEpisodes() } returns Either.Left(Failure.ServerError)
        runBlocking { seasonsRepository.getSeasons() }
        coVerify(exactly = 1) { remoteProvider.getEpisodes() }
        coVerify(exactly = 0) { cacheProvider.setEpisodes(episodes) }
    }
}