package com.juangomez.domain.models

data class Characters(private val characters: List<Character>) {

    fun get() = characters

    fun size() = characters.size
}

fun List<Character>.toCharacters() = Characters(this)