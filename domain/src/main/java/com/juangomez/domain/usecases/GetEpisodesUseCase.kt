package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episodes
import com.juangomez.domain.repositories.EpisodeRepository

class GetEpisodesUseCase(private val episodeRepository: EpisodeRepository) :
    BaseUseCase<Episodes, BaseUseCase.None>() {

    override suspend fun run(params: None?): Either<Failure, Episodes> =
        episodeRepository.getEpisodes()
}