package com.juangomez.rickandmorty

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Season
import com.juangomez.domain.usecases.BaseUseCase

class GetSeasonsMockUseCase :
    BaseUseCase<List<Season>, BaseUseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Season>> =
        Either.Right(
            listOf(
                Season(1, emptyList()),
                Season(2, emptyList())
            )
        )
}