package com.juangomez.cache

import com.juangomez.data.providers.cache.CharacterCacheProvider
import com.juangomez.domain.models.Character
import com.juangomez.domain.models.SummaryLocation
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CharacterCacheProviderTest {

    private lateinit var characterCacheProvider: CharacterCacheProvider
    private lateinit var characters: List<Character>

    @BeforeEach
    fun setup() {
        characterCacheProvider = CharacterCacheProviderImpl()
        characters = populateCharacterCache()
    }

    @Test
    fun `should store a list of characters and get the same list`() {
        val characterIds = listOf(1, 183)
        assertEquals(characters, characterCacheProvider.getCharactersById(characterIds))
    }

    private fun populateCharacterCache(): List<Character> {
        val summaryLocation = SummaryLocation(1, "")
        val characters = listOf(
            Character(1, "Sample 1", "", "", "", "", summaryLocation, summaryLocation, "", listOf()),
            Character(183, "Sample 2", "", "", "", "", summaryLocation, summaryLocation, "", listOf())
        )

        characterCacheProvider.setCharacters(characters)
        return characters
    }
}