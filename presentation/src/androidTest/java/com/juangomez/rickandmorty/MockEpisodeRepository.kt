package com.juangomez.rickandmorty

import com.juangomez.common.Either
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository
import java.text.SimpleDateFormat
import java.util.*

class MockEpisodeRepository : EpisodeRepository {
    private val episodes = mapOf(
        1 to Episode(1, "A day at the races", "1/1/2020".toDate(), 1, 1, listOf(1, 2, 3)),
        2 to Episode(2, "One measure, one metric", "1/2/2020".toDate(), 1, 2, listOf(1, 2, 3)),
        3 to Episode(3, "The doll", "1/3/2020".toDate(), 1, 3, listOf(1, 2)),
        4 to Episode(4, "Things go well", "1/4/2020".toDate(), 1, 4, listOf(1, 2)),
        5 to Episode(5, "Bjorn slippy", "1/5/2020".toDate(), 2, 1, listOf(1, 2, 3)),
        6 to Episode(6, "The underdog", "1/6/2020".toDate(), 2, 2, listOf(1, 2, 3)),
        7 to Episode(7, "Sat and vice", "1/7/2020".toDate(), 2, 3, listOf(1, 2)),
        8 to Episode(8, "Tremendous mess", "1/8/2020".toDate(), 2, 4, listOf(1, 2))
    )

    private fun String.toDate() =
        SimpleDateFormat("dd/MM/YYYY").parse(this)

    override suspend fun getEpisodes(): Either<Failure, List<Episode>> =
        Either.Right(episodes.map { it.value })

    override suspend fun getEpisode(id: Int): Either<Failure, Episode> =
        Either.Right(episodes[id]!!)
}