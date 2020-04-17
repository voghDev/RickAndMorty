package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Season
import com.juangomez.domain.repositories.SeasonRepository

class GetSeasonsUseCase(private val seasonRepository: SeasonRepository) :
    BaseUseCase<List<Season>, BaseUseCase.None>() {

    override suspend fun run(params: None?): Either<Failure, List<Season>> =
        seasonRepository.getSeasons()
}