package com.juangomez.remote

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.remote.models.toEpisodes
import com.juangomez.remote.responses.GetEpisodesResponse
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

class GetEpisodesTest : BaseRemoteTest() {

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
    fun `should get a valid response with single page`() {
        val jsonResponse = JSONFile.GET_EPISODES_SINGLE_PAGE_RESPONSE.string()
        val pojoResponse = pojo(jsonResponse, GetEpisodesResponse::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.OK.code)
                .setBody(jsonResponse)
        )

        runBlocking {
            val response = episodeRemoteProvider.getEpisodes()
            assertEquals(
                Either.Right(pojoResponse.results.toEpisodes()),
                response
            )
        }
    }

    @Test
    fun `should get a valid response with multiple pages`() {
        val jsonFirstResponse = JSONFile.GET_EPISODES_MULTIPLE_PAGES_FIRST_RESPONSE.string()
        val jsonSecondResponse = JSONFile.GET_EPISODES_MULTIPLE_PAGES_SECOND_RESPONSE.string()
        val pojoFirstResponse = pojo(jsonFirstResponse, GetEpisodesResponse::class.java)
        val pojoSecondResponse = pojo(jsonSecondResponse, GetEpisodesResponse::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.OK.code)
                .setBody(jsonFirstResponse)
        )

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.OK.code)
                .setBody(jsonSecondResponse)
        )

        val finalResponse = pojoFirstResponse.results.toEpisodes().union(pojoSecondResponse.results.toEpisodes()).toList()

        runBlocking {
            val response = episodeRemoteProvider.getEpisodes()
            assertEquals(
                Either.Right(finalResponse),
                response
            )
        }
    }

    @Test
    fun `should get a failure response with server error code`() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.INTERNAL_SERVER_ERROR.code)
        )

        runBlocking {
            val response = episodeRemoteProvider.getEpisodes()
            assertEquals(
                Either.Left(Failure.ServerError),
                response
            )
        }
    }
}