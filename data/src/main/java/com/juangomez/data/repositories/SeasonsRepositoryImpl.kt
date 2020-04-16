package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.data.providers.CacheProvider
import com.juangomez.data.providers.RemoteProvider
import com.juangomez.domain.models.Seasons
import com.juangomez.domain.repositories.SeasonsRepository

class SeasonsRepositoryImpl(
    private val remoteProvider: RemoteProvider,
    private val cacheProvider: CacheProvider
) : SeasonsRepository {

    override suspend fun getSeasons(): Either<Failure, Seasons> =
        remoteProvider.getEpisodes().map { episodes ->
            val seasons = episodes.groupBySeasons()
            cacheProvider.setEpisodes(episodes)
            seasons
        }
}