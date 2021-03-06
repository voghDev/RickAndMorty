package com.juangomez.data.providers.cache

import com.juangomez.domain.models.Character

interface CharacterCacheProvider {

    fun getCharactersById(ids: List<Int>): List<Character>

    fun setCharacters(characters: List<Character>)
}