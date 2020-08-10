package com.juangomez.domain.repositories

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode

interface EpisodeRepository {

    suspend fun getEpisodes(): CEither<Failure, List<Episode>>

    suspend fun getEpisode(id: Int): CEither<Failure, Episode>
}