package com.juangomez.remote.services.characters

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.CharacterRemoteDataSource
import com.juangomez.domain.models.Character
import com.juangomez.remote.models.toCharacters
import com.juangomez.remote.services.APIService

class CharacterRemoteDataSourceImpl(private val api: APIService<CharacterAPIService>) :
    CharacterRemoteDataSource {

    override suspend fun getCharactersById(ids: List<Int>): Either<Failure, List<Character>> =
        api.execute { api.service.getEpisodeById(ids).toCharacters() }

}