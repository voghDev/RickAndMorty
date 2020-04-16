package com.juangomez.data.providers

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episodes

interface RemoteProvider {

    fun getEpisodes(): Either<Failure, Episodes>
}