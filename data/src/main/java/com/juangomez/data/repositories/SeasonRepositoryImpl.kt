package com.juangomez.data.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.common.map
import com.juangomez.data.providers.cache.CacheProvider
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.domain.models.Season
import com.juangomez.domain.models.groupBySeasons
import com.juangomez.domain.repositories.SeasonRepository

class SeasonRepositoryImpl(
    private val episodesRemoteProvider: EpisodesRemoteProvider,
    private val cacheProvider: CacheProvider
) : SeasonRepository {

    override suspend fun getSeasons(): Either<Failure, List<Season>> =
        episodesRemoteProvider.getEpisodes().map { episodes ->
            val seasons = episodes.groupBySeasons()
            cacheProvider.setEpisodes(episodes)
            seasons
        }
}