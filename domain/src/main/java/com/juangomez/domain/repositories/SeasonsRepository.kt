package com.juangomez.domain.repositories

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episodes
import com.juangomez.domain.models.Seasons

interface SeasonsRepository {

    suspend fun getSeasons(): Either<Failure, Seasons>
}