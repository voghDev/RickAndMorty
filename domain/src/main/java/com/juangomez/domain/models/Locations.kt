package com.juangomez.domain.models

data class Locations(val locations: List<Location>)

fun List<Location>.toLocations() = Locations(this)