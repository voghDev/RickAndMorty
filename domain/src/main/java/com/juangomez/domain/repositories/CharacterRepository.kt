package com.juangomez.domain.repositories

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character

interface CharacterRepository {

    suspend fun getCharactersById(ids: List<Int>): CEither<Failure, List<Character>>
}