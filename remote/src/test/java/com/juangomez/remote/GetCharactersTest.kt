package com.juangomez.remote

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.CharacterRemoteProvider
import com.juangomez.remote.models.RemoteCharacter
import com.juangomez.remote.models.toCharacter
import com.juangomez.remote.models.toCharacters
import com.juangomez.remote.services.APIService
import com.juangomez.remote.services.characters.CharacterAPIService
import com.juangomez.remote.services.characters.CharacterRemoteProviderImpl
import com.juangomez.remote.util.JSONFile
import com.juangomez.remote.util.pojo
import com.juangomez.remote.util.pojoList
import com.juangomez.remote.util.string
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import org.junit.Test

class GetCharactersTest : BaseRemoteTest() {

    private lateinit var charactersRemoteProvider: CharacterRemoteProvider

    override fun setup() {
        super.setup()
        charactersRemoteProvider = CharacterRemoteProviderImpl(
            APIService(
                CharacterAPIService::class.java,
                BASE_URL,
                provideGsonConverterFactory(),
                provideInterceptors()
            )
        )
    }

    @Test
    fun `should get a valid response`() {
        val charactersId = listOf(1, 183)
        val jsonResponse = JSONFile.GET_CHARACTERS_RESPONSE.string()
        val pojoResponse = pojoList(jsonResponse, RemoteCharacter::class.java)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.OK.code)
                .setBody(jsonResponse)
        )

        runBlocking {
            val response = charactersRemoteProvider.getCharactersById(charactersId)
            assertEquals(
                Either.Right(pojoResponse.toCharacters()),
                response
            )
        }
    }

    @Test
    fun `should get a failure response with server error code`() {
        val charactersId = listOf(1, 183)

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(Status.INTERNAL_SERVER_ERROR.code)
        )

        runBlocking {
            val response = charactersRemoteProvider.getCharactersById(charactersId)
            assertEquals(
                Either.Left(Failure.ServerError),
                response
            )
        }
    }
}