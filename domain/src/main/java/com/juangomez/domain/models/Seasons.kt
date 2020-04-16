package com.juangomez.domain.models

data class Seasons(val seasons: List<Season>)

fun List<Season>.toSeasons() = Seasons(this)