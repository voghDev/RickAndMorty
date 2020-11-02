package com.juangomez.data.repositories

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.cache.EpisodeCacheDataSource
import com.juangomez.data.providers.remote.EpisodeRemoteDataSource
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository

class EpisodeRepositoryImpl(
    private val episodeRemoteDataSource: EpisodeRemoteDataSource,
    private val episodeCacheDataSource: EpisodeCacheDataSource
) : EpisodeRepository {

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> =
        episodeRemoteDataSource.getEpisodes().map { remoteEpisodes ->
            episodeCacheDataSource.setEpisodes(remoteEpisodes)
            remoteEpisodes
        }

    override suspend fun getEpisode(id: Int): Either<Failure, Episode> {
        val cachedEpisode = episodeCacheDataSource.getEpisodeById(id)
        return if (cachedEpisode != null) {
            Either.Right(cachedEpisode)
        } else {
            episodeRemoteDataSource.getEpisodeById(id).map { remoteEpisode ->
                episodeCacheDataSource.setEpisode(remoteEpisode)
                remoteEpisode
            }
        }
    }
}