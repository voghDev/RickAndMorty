package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episodes
import com.juangomez.domain.models.Seasons
import com.juangomez.domain.repositories.SeasonsRepository

class GetSeasonsUseCase(private val seasonsRepository: SeasonsRepository) :
    BaseUseCase<Seasons, BaseUseCase.None>() {

    override suspend fun run(params: None?): Either<Failure, Seasons> =
        seasonsRepository.getSeasons()
}