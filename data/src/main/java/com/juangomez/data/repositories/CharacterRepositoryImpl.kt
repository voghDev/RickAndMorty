package com.juangomez.data.repositories

import arrow.core.Either
import arrow.core.flatMap
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.CharacterCacheDataSource
import com.juangomez.data.providers.remote.CharacterRemoteDataSource
import com.juangomez.domain.models.Character
import com.juangomez.domain.repositories.CharacterRepository

class CharacterRepositoryImpl(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val characterCacheDataSource: CharacterCacheDataSource
) : CharacterRepository {

    override suspend fun getCharactersById(ids: List<Int>): Either<Failure, List<Character>> {
        val cachedCharacters = characterCacheDataSource.getCharactersById(ids)
        val cachedCharacterIds = cachedCharacters.map { character -> character.id }
        val characterIdsToGetFromRemote = ids.filterNot { it in cachedCharacterIds }

        return if (characterIdsToGetFromRemote.isEmpty()) Either.Right(cachedCharacters)
        else characterRemoteDataSource.getCharactersById(characterIdsToGetFromRemote)
                .flatMap { characters ->
                    characterCacheDataSource.setCharacters(characters)
                    Either.Right(characters)
                }
    }
}