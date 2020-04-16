package com.juangomez.domain.models

data class Locations(private val locations: List<Location>) {

    fun get() = locations

    fun size() = locations.size
}

fun List<Location>.toLocations() = Locations(this)