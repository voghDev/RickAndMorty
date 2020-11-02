package com.juangomez.cache

import com.juangomez.data.providers.cache.EpisodeCacheDataSource
import com.juangomez.domain.models.Episode
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class EpisodeCacheDataSourceTest {

    private lateinit var episodeCacheDataSource: EpisodeCacheDataSource
    private lateinit var episodes: List<Episode>

    @Before
    fun setup() {
        episodeCacheDataSource = EpisodeCacheDataSourceImpl()
        episodes = populateEpisodeCache()
    }

    @Test
    fun `should store a list of episodes and get the same list`() {
        assertEquals(episodes, episodeCacheDataSource.getEpisodes())
    }

    @Test
    fun `should get a episode by id`() {
        val episodeToFind = episodes.last()

        assertEquals(episodeToFind, episodeCacheDataSource.getEpisodeById(episodeToFind.id))
    }

    @Test
    fun `should try to set a episode that already exists but nothing should happen`() {
        val episodeToSet = episodes.last()
        val expectedEpisodes = episodes.toList()

        episodeCacheDataSource.setEpisode(episodeToSet)

        assertEquals(expectedEpisodes,  episodeCacheDataSource.getEpisodes())
    }

    @Test
    fun `should set a episode that does not exist`() {
        val episodeToSet = Episode(8547, "Sample 4", Date(), 4, 1, listOf())
        val expectedEpisodes = episodes.union(listOf(episodeToSet)).toList()

        episodeCacheDataSource.setEpisode(episodeToSet)

        assertEquals(expectedEpisodes, episodeCacheDataSource.getEpisodes())
    }

    private fun populateEpisodeCache(): List<Episode> {
        val episodes = listOf(
            Episode(1468, "Sample 1", Date(), 1, 1, listOf()),
            Episode(2232, "Sample 2", Date(), 2, 1, listOf()),
            Episode(1567, "Sample 3", Date(), 3, 1, listOf())
        )

        episodeCacheDataSource.setEpisodes(episodes)
        return episodes
    }
}