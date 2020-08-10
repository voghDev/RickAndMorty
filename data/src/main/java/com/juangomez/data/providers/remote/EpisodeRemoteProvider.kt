package com.juangomez.data.providers.remote

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode

interface EpisodeRemoteProvider {

    suspend fun getEpisodes(): CEither<Failure, List<Episode>>

    suspend fun getEpisodeById(id: Int): CEither<Failure, Episode>
}