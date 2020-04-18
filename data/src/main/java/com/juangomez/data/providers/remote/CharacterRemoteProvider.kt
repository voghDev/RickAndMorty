package com.juangomez.data.providers.remote

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character

interface CharacterRemoteProvider {

    suspend fun getCharactersById(ids: List<Int>): Either<Failure, List<Character>>
}