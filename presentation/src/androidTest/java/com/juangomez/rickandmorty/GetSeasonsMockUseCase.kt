package com.juangomez.rickandmorty

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.domain.models.Season
import com.juangomez.domain.models.groupBySeasons
import com.juangomez.domain.repositories.EpisodeRepository
import com.juangomez.domain.usecases.BaseUseCase

class GetSeasonsMockUseCase() :
    BaseUseCase<List<Season>, BaseUseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Season>> =
        Either.Right(emptyList<Season>())
}