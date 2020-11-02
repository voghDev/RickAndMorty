package com.juangomez.data.providers.remote

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character

interface CharacterRemoteDataSource {

    suspend fun getCharactersById(ids: List<Int>): Either<Failure, List<Character>>
}