package com.juangomez.domain.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episodes

interface EpisodeRepository {

    suspend fun getEpisodes(): Either<Failure, Episodes>
}