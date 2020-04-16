package com.juangomez.domain.models

data class Characters(val characters: List<Character>)

fun List<Character>.toCharacters() = Characters(this)