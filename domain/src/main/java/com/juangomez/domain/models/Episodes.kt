package com.juangomez.domain.models

data class Episodes(private val episodes: List<Episode>) {

    fun groupBySeasons(): Seasons = episodes
        .groupBy { it.season }
        .map { Season(it.key, it.value.toEpisodes()) }
        .toSeasons()

    fun get() = episodes

    fun size() = episodes.size
}

fun List<Episode>.toEpisodes() = Episodes(this)