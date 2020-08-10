package com.juangomez.remote

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.toEpisode
import com.juangomez.remote.services.APIService
import com.juangomez.remote.services.episodes.EpisodeAPIService
import com.juangomez.remote.services.episodes.EpisodeRemoteProviderImpl
import com.juangomez.remote.util.JSONFile
import com.juangomez.remote.util.pojo
import com.juangomez.remote.util.string
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

class GetEpisodeTest : BaseRemoteTest() {

    private lateinit var episodeRemoteProvider: EpisodeRemoteProvider

    override fun setup() {
        super.setup()
        episodeRemoteProvider = EpisodeRemoteProviderImpl(
            APIService(
                EpisodeAPIService::class.java,
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
            val response = episodeRemoteProvider.getEpisodeById(episodeId)
            assertEquals(
                CEither.Right(pojoResponse.toEpisode()),
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
            val response = episodeRemoteProvider.getEpisodeById(episodeId)
            assertEquals(
                CEither.Left(Failure.ServerError),
                response
            )
        }
    }
}