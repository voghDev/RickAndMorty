package com.juangomez.rickandmorty

import com.juangomez.common.CEither
import com.juangomez.common.Failure
import com.juangomez.domain.models.Episode
import com.juangomez.domain.repositories.EpisodeRepository
import java.text.SimpleDateFormat

class MockEpisodeRepository : EpisodeRepository {
    private val episodes = mapOf(
        1 to Episode(1, "Pilot", "2/12/2013".toDate(), 1, 1, listOf(1, 2, 3)),
        2 to Episode(2, "Lawnmower Dog", "9/12/2013".toDate(), 1, 2, listOf(1, 2, 3)),
        3 to Episode(3, "Anatomy Park", "16/12/2013".toDate(), 1, 3, listOf(1, 2)),
        4 to Episode(4, "M. Night Shaym-Aliens!", "1/4/2013".toDate(), 1, 4, listOf(1, 2)),
        5 to Episode(5, "A Rickle in Time", "26/7/2015".toDate(), 2, 1, listOf(1, 2, 3)),
        6 to Episode(6, "Mortynight Run", "2/8/2015".toDate(), 2, 2, listOf(1, 2, 3)),
        7 to Episode(7, "Auto Erotic Assimilation", "9/8/2015".toDate(), 2, 3, listOf(1, 2)),
        8 to Episode(8, "Total Rickall", "16/8/2015".toDate(), 2, 4, listOf(1, 2))
    )

    private fun String.toDate() =
        SimpleDateFormat("dd/MM/YYYY").parse(this)

    override suspend fun getEpisodes(): CEither<Failure, List<Episode>> =
        CEither.Right(episodes.map { it.value })

    override suspend fun getEpisode(id: Int): CEither<Failure, Episode> =
        CEither.Right(episodes[id]!!)
}
