package com.juangomez.cache

import com.juangomez.data.providers.cache.CharacterCacheDataSource
import com.juangomez.domain.models.Character

class CharacterCacheDataSourceImpl: CharacterCacheDataSource {

    private var characters = mutableListOf<Character>()

    override fun getCharactersById(ids: List<Int>) = characters

    override fun setCharacters(characters: List<Character>) {
        this.characters = characters.toMutableList()
    }
}