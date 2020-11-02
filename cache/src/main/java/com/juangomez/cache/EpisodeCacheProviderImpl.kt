package com.juangomez.cache

import com.juangomez.data.providers.cache.EpisodeCacheDataSource
import com.juangomez.domain.models.Episode

class EpisodeCacheDataSourceImpl : EpisodeCacheDataSource {

    private var episodes = mutableListOf<Episode>()

    override fun getEpisodes() = episodes

    override fun setEpisodes(episodes: List<Episode>) {
        this.episodes = episodes.toMutableList()
    }

    override fun getEpisodeById(id: Int) = episodes.find { episode -> episode.id == id }

    override fun setEpisode(episode: Episode) {
        if(getEpisodeById(episode.id) == null) episodes.add(episode)
    }

}