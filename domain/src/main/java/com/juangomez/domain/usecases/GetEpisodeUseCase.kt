package com.juangomez.domain.usecases

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository

class GetEpisodeUseCase(private val episodeRepository: EpisodeRepository) :
    BaseUseCase<Episode, GetEpisodeUseCase.Params>() {

    override suspend fun run(params: Params): CEither<Failure, Episode> =
        episodeRepository.getEpisode(params.id)

    data class Params(val id: Int)
}