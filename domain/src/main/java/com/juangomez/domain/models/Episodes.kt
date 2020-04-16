package com.juangomez.domain.models

data class Episodes(val episodes: List<Episode>) {

    fun groupBySeasons(): Seasons = episodes
        .groupBy { it.season }
        .map { Season(it.key, it.value.toEpisodes()) }
        .toSeasons()
}

fun List<Episode>.toEpisodes() = Episodes(this)