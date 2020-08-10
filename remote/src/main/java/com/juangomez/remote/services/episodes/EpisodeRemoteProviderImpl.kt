package com.juangomez.remote.services.episodes

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.data.providers.remote.EpisodeRemoteProvider
import com.juangomez.domain.models.Episode
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.toEpisode
import com.juangomez.remote.models.toEpisodes
import com.juangomez.remote.services.APIService

class EpisodeRemoteProviderImpl(private val api: APIService<EpisodeAPIService>) :
    EpisodeRemoteProvider {

    override suspend fun getEpisodes(): CEither<Failure, List<Episode>> {
        var page: Int? = 1
        val episodes = mutableListOf<RemoteEpisode>()

        while (page != null) {
            when (val response = api.execute { api.service.getEpisodes(page!!) }) {
                is CEither.Right -> {
                    page = response.data.info.getNextPage()
                    episodes.addAll(response.data.results)
                }
                is CEither.Left -> return response
            }
        }

        return CEither.Right(episodes.toEpisodes())
    }

    override suspend fun getEpisodeById(id: Int): CEither<Failure, Episode> =
        api.execute { api.service.getEpisodeById(id).toEpisode() }
}