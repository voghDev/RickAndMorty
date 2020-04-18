package com.juangomez.remote.models

import com.google.gson.annotations.SerializedName
import com.juangomez.domain.models.Character

data class RemoteCharacter(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String,
    @SerializedName("type") val type: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("origin") val origin: RemoteSummaryLocation,
    @SerializedName("location") val location: RemoteSummaryLocation,
    @SerializedName("image") val image: String,
    @SerializedName("episode") val episodes: List<String>,
    @SerializedName("url") val url: String,
    @SerializedName("created") val created: String
) {

   companion object {
        private const val EPISODE_DELIMITER = "/character/"
    }

    fun getEpisodeIds() = episodes.map { it.substringAfter(EPISODE_DELIMITER).toInt() }
}

fun RemoteCharacter.toCharacter() = Character(
    id,
    name,
    status,
    species,
    type,
    gender,
    origin.toSummaryLocation(),
    location.toSummaryLocation(),
    image,
    getEpisodeIds()
)

fun List<RemoteCharacter>.toCharacters() = map { it.toCharacter() }