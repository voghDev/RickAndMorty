package com.juangomez.data.providers.remote

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode

interface EpisodesRemoteProvider {

    suspend fun getEpisodes(): Either<Failure, List<Episode>>
}