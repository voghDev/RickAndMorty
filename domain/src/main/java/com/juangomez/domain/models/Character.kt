package com.juangomez.domain.models

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: SummaryLocation,
    val location: SummaryLocation,
    val image: String,
    val episodeIds: List<Int>
)