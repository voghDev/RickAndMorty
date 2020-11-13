package com.juangomez.cache

import com.juangomez.data.providers.cache.EpisodeCacheProvider
import com.juangomez.domain.models.Episode
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class EpisodeCacheProviderTest {

    private lateinit var episodeCacheProvider: EpisodeCacheProvider
    private lateinit var episodes: List<Episode>

    @Before
    fun setup() {
        episodeCacheProvider = EpisodeCacheProviderImpl()
        episodes = populateEpisodeCache()
    }

    @Test
    fun `should store a list of episodes and get the same list`() {
        assertEquals(episodes, episodeCacheProvider.getEpisodes())
    }

    @Test
    fun `should get an episode by id`() {
        val episodeToFind = episodes.last()

        assertEquals(episodeToFind, episodeCacheProvider.getEpisodeById(episodeToFind.id))
    }

    @Test
    fun `should try to set a episode that already exists but nothing should happen`() {
        val episodeToSet = episodes.last()
        val expectedEpisodes = episodes.toList()

        episodeCacheProvider.setEpisode(episodeToSet)

        assertEquals(expectedEpisodes,  episodeCacheProvider.getEpisodes())
    }

    @Test
    fun `should set a episode that does not exist`() {
        val episodeToSet = Episode(8547, "Sample 4", Date(), 4, 1, listOf())
        val expectedEpisodes = episodes.union(listOf(episodeToSet)).toList()

        episodeCacheProvider.setEpisode(episodeToSet)

        assertEquals(expectedEpisodes, episodeCacheProvider.getEpisodes())
    }

    private fun populateEpisodeCache(): List<Episode> {
        val episodes = listOf(
            Episode(1468, "Sample 1", Date(), 1, 1, listOf()),
            Episode(2232, "Sample 2", Date(), 2, 1, listOf()),
            Episode(1567, "Sample 3", Date(), 3, 1, listOf())
        )

        episodeCacheProvider.setEpisodes(episodes)
        return episodes
    }
}