package com.juangomez.domain.models

data class Seasons(private val seasons: List<Season>) {

    fun get() = seasons

    fun size() = seasons.size
}

fun List<Season>.toSeasons() = Seasons(this)