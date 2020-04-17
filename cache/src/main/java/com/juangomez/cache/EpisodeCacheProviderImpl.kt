package com.juangomez.cache

import com.juangomez.data.providers.cache.EpisodeCacheProvider
import com.juangomez.domain.models.Episode

class EpisodeCacheProviderImpl : EpisodeCacheProvider {

    private var episodes = mutableListOf<Episode>()

    override fun setEpisodes(episodes: List<Episode>) {
        this.episodes = episodes.toMutableList()
    }

    override fun getEpisode(id: Int): Episode? = episodes.find { episode -> episode.id == id }

    override fun setEpisode(episode: Episode) {
        if(getEpisode(episode.id) == null) episodes.add(episode)
    }

}