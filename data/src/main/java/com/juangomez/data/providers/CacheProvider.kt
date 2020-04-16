package com.juangomez.data.providers

import com.juangomez.domain.models.Episodes

interface CacheProvider {

    fun setEpisodes(episodes: Episodes)
}