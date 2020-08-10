package com.juangomez.data.repositories

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.CharacterCacheProvider
import com.juangomez.data.providers.remote.CharacterRemoteProvider
import com.juangomez.domain.models.Character
import com.juangomez.domain.models.SummaryLocation
import com.juangomez.domain.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharacterRepositoryTest {

    private lateinit var characterRepository: CharacterRepository

    @MockK
    private lateinit var characterRemoteProvider: CharacterRemoteProvider

    @MockK
    private lateinit var characterCacheProvider: CharacterCacheProvider

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to cache provider when get characters by id and get a successful response`() {
        val characterIds = listOf(1, 183)
        val summaryLocation = SummaryLocation(1, "")
        val characters = listOf(
            Character(1, "Sample 1", "", "", "", "", summaryLocation, summaryLocation, "", listOf()),
            Character(183, "Sample 2", "", "", "", "", summaryLocation, summaryLocation, "", listOf())
        )

        characterRepository = CharacterRepositoryImpl(characterRemoteProvider, characterCacheProvider)

        coEvery { characterCacheProvider.getCharactersById(characterIds) } returns characters
        runBlocking { characterRepository.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterCacheProvider.getCharactersById(characterIds) }
        coVerify(exactly = 0) { characterRemoteProvider.getCharactersById(characterIds) }
    }

    @Test
    fun `should call to cache and remote provider when get characters and get a successful response`() {
        val characterIds = listOf(1, 183)
        val emptyCharactersList = emptyList<Character>()
        val characters = mockk<List<Character>>()

        characterRepository = CharacterRepositoryImpl(characterRemoteProvider, characterCacheProvider)

        coEvery { characterCacheProvider.getCharactersById(characterIds) } returns emptyCharactersList
        coEvery { characterRemoteProvider.getCharactersById(characterIds) } returns CEither.Right(characters)
        coEvery { characterCacheProvider.setCharacters(characters) } returns Unit
        runBlocking { characterRepository.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterCacheProvider.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterRemoteProvider.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterCacheProvider.setCharacters(characters) }
    }

    @Test
    fun `should call to cache and remote providers when get character and get a failed response`() {
        val characterIds = listOf(1, 183)
        val emptyCharactersList = emptyList<Character>()
        val characters = mockk<List<Character>>()

        characterRepository = CharacterRepositoryImpl(characterRemoteProvider, characterCacheProvider)

        coEvery { characterCacheProvider.getCharactersById(characterIds) } returns emptyCharactersList
        coEvery { characterRemoteProvider.getCharactersById(characterIds) } returns CEither.Left(Failure.ServerError)
        coEvery { characterCacheProvider.setCharacters(characters) } returns Unit
        runBlocking { characterRepository.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterCacheProvider.getCharactersById(characterIds) }
        coVerify(exactly = 1) { characterRemoteProvider.getCharactersById(characterIds) }
        coVerify(exactly = 0) { characterCacheProvider.setCharacters(characters) }
    }
}