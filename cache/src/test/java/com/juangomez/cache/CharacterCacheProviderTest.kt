package com.juangomez.cache

import com.juangomez.data.providers.cache.CharacterCacheDataSource
import com.juangomez.domain.models.Character
import com.juangomez.domain.models.SummaryLocation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharacterCacheDataSourceTest {

    private lateinit var characterCacheDataSource: CharacterCacheDataSource
    private lateinit var characters: List<Character>

    @Before
    fun setup() {
        characterCacheDataSource = CharacterCacheDataSourceImpl()
        characters = populateCharacterCache()
    }

    @Test
    fun `should store a list of characters and get the same list`() {
        val characterIds = listOf(1, 183)
        assertEquals(characters, characterCacheDataSource.getCharactersById(characterIds))
    }

    private fun populateCharacterCache(): List<Character> {
        val summaryLocation = SummaryLocation(1, "")
        val characters = listOf(
            Character(1, "Sample 1", "", "", "", "", summaryLocation, summaryLocation, "", listOf()),
            Character(183, "Sample 2", "", "", "", "", summaryLocation, summaryLocation, "", listOf())
        )

        characterCacheDataSource.setCharacters(characters)
        return characters
    }
}