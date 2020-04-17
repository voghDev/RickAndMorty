package com.juangomez.domain.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Season

interface SeasonRepository {

    suspend fun getSeasons(): Either<Failure, List<Season>>
}