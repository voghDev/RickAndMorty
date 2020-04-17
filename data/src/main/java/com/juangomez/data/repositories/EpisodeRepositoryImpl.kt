package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.CacheProvider
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository

class EpisodeRepositoryImpl(
    private val episodesRemoteProvider: EpisodesRemoteProvider,
    private val cacheProvider: CacheProvider
) : EpisodeRepository {

    override suspend fun getEpisode(id: Int): Either<Failure, Episode> {
        val cachedEpisode = cacheProvider.getEpisode(id)
        return if (cachedEpisode != null) {
            Either.Right(cachedEpisode)
        } else {
            val remoteEpisode = episodesRemoteProvider.getEpisode(id)
            if (remoteEpisode.isRight) cacheProvider.setEpisode((remoteEpisode as Either.Right).data)
            remoteEpisode
        }
    }
}