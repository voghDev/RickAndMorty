package com.juangomez.remote.models

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class RemoteEpisodeTest {

    @Test
    fun `should get a valid date from air date string`() {
        val airDate = "December 13, 2019"
        val episode = RemoteEpisode(1, "", airDate, "", listOf(), "", "")

        val expectedDate = GregorianCalendar(2019, Calendar.DECEMBER, 13).time
        val episodeAirDate = episode.airDateStringToDate()

        assert(expectedDate == episodeAirDate)
    }

    @Test
    fun `should get a valid season number from episode string`() {
        val episodeUrl = "S01E25"
        val episode = RemoteEpisode(1, "", "", episodeUrl, listOf(), "", "")

        val expectedSeason = 1
        val episodeSeason = episode.getSeasonNumber()

        assertEquals(expectedSeason, episodeSeason)
    }

    @Test
    fun `should get a valid episode number from episode string`() {
        val episodeUrl = "S01E25"
        val episode = RemoteEpisode(1, "", "", episodeUrl, listOf(), "", "")

        val expectedSeason = 25
        val episodeSeason = episode.getEpisodeNumber()

        assertEquals(expectedSeason, episodeSeason)
    }

    @Test
    fun `should get a valid character ids list of ints from episode character id list of urls`() {
        val characterIds = listOf(
            "https://rickandmortyapi.com/api/character/1",
            "https://rickandmortyapi.com/api/character/2"
        )
        val episode = RemoteEpisode(1, "", "", "", characterIds, "", "")

        val expectedCharacterIds = listOf(1, 2)
        val episodeCharacterIds = episode.getCharacterIds()

        assertEquals(expectedCharacterIds, episodeCharacterIds)
    }
}