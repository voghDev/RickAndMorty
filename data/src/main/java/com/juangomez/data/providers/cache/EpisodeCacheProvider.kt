package com.juangomez.data.providers.cache

import com.juangomez.domain.models.Episode

interface EpisodeCacheDataSource {

    fun getEpisodes(): List<Episode>

    fun setEpisodes(episodes: List<Episode>)

    fun getEpisodeById(id: Int): Episode?

    fun setEpisode(episode: Episode)
}