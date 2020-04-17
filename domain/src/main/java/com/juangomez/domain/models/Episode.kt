package com.juangomez.domain.models

import java.util.*

data class Episode(
    val id: Int,
    val name: String,
    val airDate: Date,
    val season: Int,
    val number: Int,
    val characterIds: List<Int>
)

fun List<Episode>.groupBySeasons() = groupBy { it.season }
    .map { Season(it.key, it.value) }