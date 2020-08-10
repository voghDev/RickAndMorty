package com.juangomez.data.repositories

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.data.providers.cache.CharacterCacheProvider
import com.juangomez.data.providers.remote.CharacterRemoteProvider
import com.juangomez.domain.models.Character
import com.juangomez.domain.repositories.CharacterRepository

class CharacterRepositoryImpl(
    private val characterRemoteProvider: CharacterRemoteProvider,
    private val characterCacheProvider: CharacterCacheProvider
) : CharacterRepository {

    override suspend fun getCharactersById(ids: List<Int>): CEither<Failure, List<Character>> {
        val cachedCharacters = characterCacheProvider.getCharactersById(ids)
        val cachedCharacterIds = cachedCharacters.map { character -> character.id }
        val characterIdsToGetFromRemote = ids.filterNot { it in cachedCharacterIds }

        return if (characterIdsToGetFromRemote.isEmpty()) CEither.Right(cachedCharacters)
        else characterRemoteProvider.getCharactersById(characterIdsToGetFromRemote)
            .map { characters ->
                characterCacheProvider.setCharacters(characters)
                characters
            }
    }
}