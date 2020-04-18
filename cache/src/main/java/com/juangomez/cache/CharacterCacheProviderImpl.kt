package com.juangomez.cache

import com.juangomez.data.providers.cache.CharacterCacheProvider
import com.juangomez.domain.models.Character

class CharacterCacheProviderImpl: CharacterCacheProvider {

    private var characters = mutableListOf<Character>()

    override fun getCharactersById(ids: List<Int>) = characters

    override fun setCharacters(characters: List<Character>) {
        this.characters = characters.toMutableList()
    }
}