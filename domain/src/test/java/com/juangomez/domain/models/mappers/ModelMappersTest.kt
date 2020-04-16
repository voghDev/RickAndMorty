package com.juangomez.domain.models.mappers

import com.juangomez.domain.models.*
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class ModelMappersTest {

    @MockK
    private lateinit var location: Location

    @MockK
    private lateinit var character: Character

    @MockK
    private lateinit var season: Season

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should return a Locations object given a list of Location objects with the same size`() {
        val locationList = listOf(location)
        val locations = locationList.toLocations()

        assert(locationList.size == locations.size())
    }

    @Test
    fun `should return a Characters object given a list of Character objects with the same size`() {
        val characterList = listOf(character)
        val characters = characterList.toCharacters()

        assert(characterList.size == characters.size())
    }

    @Test
    fun `should return a Seasons object given a list of Season objects with the same size`() {
        val seasonList = listOf(season)
        val seasons = seasonList.toSeasons()

        assert(seasonList.size == seasons.size())
    }

    @Test
    fun `should return a Seasons object given a Episodes grouped by season number being three seasons with just one episode for each one`() {
        val episodes = Episodes(
            listOf(
                Episode(1468, "Sample 1", Date(), 1, 1, listOf()),
                Episode(2232, "Sample 2", Date(), 2, 1, listOf()),
                Episode(1567, "Sample 3", Date(), 3, 1, listOf())
            )
        )
        val seasons = episodes.groupBySeasons()

        assert(seasons.size() == 3)
        seasons.get().forEach { assert(it.episodes.size() == 1) }
    }

    @Test
    fun `should return a Seasons object given a Episodes grouped by season number being three seasons with two episodes for each one`() {
        val episodes = Episodes(
            listOf(
                Episode(1468, "Sample 1", Date(), 1, 1, listOf()),
                Episode(1469, "Sample 2", Date(), 1, 2, listOf()),
                Episode(2232, "Sample 3", Date(), 2, 1, listOf()),
                Episode(2233, "Sample 4", Date(), 2, 2, listOf()),
                Episode(1567, "Sample 5", Date(), 3, 1, listOf()),
                Episode(1568, "Sample 6", Date(), 3, 2, listOf())
            )
        )
        val seasons = episodes.groupBySeasons()

        assert(seasons.size() == 3)
        seasons.get().forEach { assert(it.episodes.size() == 2) }
    }


}