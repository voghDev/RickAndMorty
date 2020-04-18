package com.juangomez.domain.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character

interface CharacterRepository {

    suspend fun getCharactersById(ids: List<Int>): Either<Failure, List<Character>>
}