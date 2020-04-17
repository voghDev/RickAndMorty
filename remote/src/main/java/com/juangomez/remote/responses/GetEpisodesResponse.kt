package com.juangomez.remote.responses

import com.google.gson.annotations.SerializedName
import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.models.RemotePaginationInfo

data class GetEpisodesResponse(
    @SerializedName("info") val info: RemotePaginationInfo,
    @SerializedName("results") val results: List<RemoteEpisode>
)