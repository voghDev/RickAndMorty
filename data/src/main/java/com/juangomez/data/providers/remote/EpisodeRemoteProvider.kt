package com.juangomez.data.providers.remote

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode

interface EpisodeRemoteProvider {

    suspend fun getEpisodes(): Either<Failure, List<Episode>>

    suspend fun getEpisodeById(id: Int): Either<Failure, Episode>
}