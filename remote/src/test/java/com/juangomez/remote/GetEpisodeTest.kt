package com.juangomez.remote

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.toEpisode
import com.juangomez.remote.models.toEpisodes
import com.juangomez.remote.responses.GetEpisodesResponse
import com.juangomez.remote.services.APIService
import com.juangomez.remote.services.episodes.EpisodesAPIService
import com.juangomez.remote.services.episodes.EpisodesRemoteProviderImpl
import com.juangomez.remote.util.JSONFile
import com.juangomez.remote.util.pojo
import com.juangomez.remote.util.string
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

class GetEpisodeTest : BaseRemoteTest() {

    private lateinit var episodesRemoteProvider: EpisodesRemoteProvider

    override fun setup() {
        super.setup()
        episodesRemoteProvider = EpisodesRemoteProviderImpl(
            APIService(
                EpisodesAPIService::class.java,
                BASE_URL,
                provideGsonConverterFactory(),
                provideInterceptors()
            )
        )
    }

    @Test
    fun `should get a valid response`() {
        val episodeId = 1
        val jsonResponse = JSONFile.GET_EPISODE_RESPONSE.string()
        val pojoResponse = pojo(jsonResponse, RemoteEpisode::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.OK.code)
                .setBody(jsonResponse)
        )

        runBlocking {
            val response = episodesRemoteProvider.getEpisode(episodeId)
            assertEquals(
                Either.Right(pojoResponse.toEpisode()),
                response
            )
        }
    }

    @Test
    fun `should get a failure response with server error code`() {
        val episodeId = 1

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.INTERNAL_SERVER_ERROR.code)
        )

        runBlocking {
            val response = episodesRemoteProvider.getEpisode(episodeId)
            assertEquals(
                Either.Left(Failure.ServerError),
                response
            )
        }
    }
}