package com.juangomez.domain.models

data class Location(
    val id: Int,
    val name: String,
    val type: String,
    val dimensions: String,
    val residents: List<Character>
)