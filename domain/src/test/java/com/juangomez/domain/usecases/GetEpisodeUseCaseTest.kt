package com.juangomez.domain.usecases

import com.juangomez.common.CEither
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

class GetEpisodeUseCaseTest {

    private lateinit var getEpisodeUseCase: GetEpisodeUseCase

    @MockK
    private lateinit var episodeRepository: EpisodeRepository

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to episode repository when executes use case`() {
        val episodeId = 1
        val episode = mockk<Episode>()
        getEpisodeUseCase = GetEpisodeUseCase(episodeRepository)

        coEvery { episodeRepository.getEpisode(episodeId) } returns CEither.Right(episode)
        runBlocking { getEpisodeUseCase.invoke(this, GetEpisodeUseCase.Params(episodeId)) }
        coVerify(exactly = 1) { episodeRepository.getEpisode(episodeId) }
    }
}