package com.juangomez.domain.usecases

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Character
import com.juangomez.domain.repositories.CharacterRepository

class GetCharactersUseCase(private val characterRepository: CharacterRepository) :
    BaseUseCase<List<Character>, GetCharactersUseCase.Params>() {

    override suspend fun run(params: Params): CEither<Failure, List<Character>> =
        characterRepository.getCharactersById(params.ids)

    data class Params(val ids: List<Int>)
}