package com.juangomez.data.providers.remote

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character

interface CharacterRemoteProvider {

    suspend fun getCharactersById(ids: List<Int>): CEither<Failure, List<Character>>
}