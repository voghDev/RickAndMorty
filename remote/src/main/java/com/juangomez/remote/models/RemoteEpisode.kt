package com.juangomez.remote.models

import com.google.gson.annotations.SerializedName
import com.juangomez.domain.models.Episode
import java.text.SimpleDateFormat
import java.util.*

data class RemoteEpisode(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("air_date") val airDate: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
) {

    companion object {
        private const val AIR_DATE_FORMAT = "MMMM d, yyyy"
        private const val SEASON_DELIMITER = "S"
        private const val EPISODE_DELIMITER = "E"
        private const val CHARACTER_DELIMITER = "/character/"
    }

    fun airDateStringToDate(): Date =
        SimpleDateFormat(AIR_DATE_FORMAT, Locale.ENGLISH).parse(airDate)

    fun getSeasonNumber() =
        episode.substringAfter(SEASON_DELIMITER).substringBefore(EPISODE_DELIMITER).toInt()

    fun getEpisodeNumber() = episode.substringAfter(EPISODE_DELIMITER).toInt()

    fun getCharacterIds() = characters.map { it.substringAfter(CHARACTER_DELIMITER).toInt() }
}

fun RemoteEpisode.toEpisode() = Episode(
    id,
    name,
    airDateStringToDate(),
    getSeasonNumber(),
    getEpisodeNumber(),
    getCharacterIds()
)

fun List<RemoteEpisode>.toEpisodes() = map { it.toEpisode() }