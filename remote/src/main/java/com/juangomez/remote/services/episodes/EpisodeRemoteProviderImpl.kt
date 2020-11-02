package com.juangomez.remote.services.episodes

import arrow.core.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodeRemoteDataSource
import com.juangomez.domain.models.Episode
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.toEpisode
import com.juangomez.remote.models.toEpisodes
import com.juangomez.remote.services.APIService

class EpisodeRemoteDataSourceImpl(private val api: APIService<EpisodeAPIService>) :
    EpisodeRemoteDataSource {

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> {
        var page: Int? = 1
        val episodes = mutableListOf<RemoteEpisode>()

        while (page != null) {
            when (val response = api.execute { api.service.getEpisodes(page!!) }) {
                is Either.Right -> {
                    page = response.b.info.getNextPage()
                    episodes.addAll(response.b.results)
                }
                is Either.Left -> return response
            }
        }

        return Either.Right(episodes.toEpisodes())
    }

    override suspend fun getEpisodeById(id: Int): Either<Failure, Episode> =
        api.execute { api.service.getEpisodeById(id).toEpisode() }
}