package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.data.providers.cache.EpisodeCacheProvider
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository

class EpisodeRepositoryImpl(
    private val episodeRemoteProvider: EpisodeRemoteProvider,
    private val episodeCacheProvider: EpisodeCacheProvider
) : EpisodeRepository {

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> =
        episodeRemoteProvider.getEpisodes().map { remoteEpisodes ->
            episodeCacheProvider.setEpisodes(remoteEpisodes)
            remoteEpisodes
        }

    override suspend fun getEpisode(id: Int): Either<Failure, Episode> {
        val cachedEpisode = episodeCacheProvider.getEpisodeById(id)
        return if (cachedEpisode != null) {
            Either.Right(cachedEpisode)
        } else {
            episodeRemoteProvider.getEpisodeById(id).map { remoteEpisode ->
                episodeCacheProvider.setEpisode(remoteEpisode)
                remoteEpisode
            }
        }
    }
}