package com.juangomez.remote.services.episodes

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodesRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.toEpisodes
import com.juangomez.remote.services.APIService

class EpisodesRemoteProviderImpl(private val api: APIService<EpisodesAPIService>) :
    EpisodesRemoteProvider {

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> {
        var page: Int? = 1
        val episodes = mutableListOf<RemoteEpisode>()

        while (page != null) {
            when (val response = api.execute { api.service.getEpisodes(page!!) }) {
                is Either.Right -> {
                    page = response.data.info.getNextPage()
                    episodes.addAll(response.data.results)
                }
                is Either.Left -> return response
            }
        }

        return Either.Right(episodes.toEpisodes())
    }
}