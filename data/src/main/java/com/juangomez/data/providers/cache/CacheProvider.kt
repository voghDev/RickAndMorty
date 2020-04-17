package com.juangomez.data.providers.cache

import com.juangomez.domain.models.Episode

interface CacheProvider {

    fun setEpisodes(episodes: List<Episode>)

    fun getEpisode(id: Int): Episode?

    fun setEpisode(episode: Episode)
}