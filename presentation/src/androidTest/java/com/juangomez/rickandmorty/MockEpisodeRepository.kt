package com.juangomez.rickandmorty

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.data.providers.cache.EpisodeCacheProvider
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository

class MockEpisodeRepository() : EpisodeRepository {

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> =
        Either.Right(emptyList())

    override suspend fun getEpisode(id: Int): Either<Failure, Episode> =
        Either.Left(Failure.ServerError)
}