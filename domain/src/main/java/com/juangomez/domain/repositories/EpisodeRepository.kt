package com.juangomez.domain.repositories

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode

interface EpisodeRepository {

    suspend fun getEpisodes(): Either<Failure, List<Episode>>

    suspend fun getEpisode(id: Int): Either<Failure, Episode>
}