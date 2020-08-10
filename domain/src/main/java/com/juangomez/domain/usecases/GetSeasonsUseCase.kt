package com.juangomez.domain.usecases

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Season
import com.juangomez.domain.models.groupBySeasons
import com.juangomez.domain.repositories.EpisodeRepository

class GetSeasonsUseCase(private val episodeRepository: EpisodeRepository) :
    BaseUseCase<List<Season>, BaseUseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Season>> =
        episodeRepository.getEpisodes().map { episodes ->
            episodes.groupBySeasons()
        }
}