package com.juangomez.remote.models

import com.google.gson.annotations.SerializedName
import com.juangomez.domain.models.SummaryLocation

data class RemoteSummaryLocation(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {

    companion object {
        private const val LOCATION_DELIMITER = "/location/"
    }

    fun getLocationId() = url.substringAfter(LOCATION_DELIMITER).toInt()
}

fun RemoteSummaryLocation.toSummaryLocation() = SummaryLocation(
    getLocationId(),
    name
)