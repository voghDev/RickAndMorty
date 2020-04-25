package com.juangomez.remote.services.episodes

import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.responses.GetEpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeAPIService {

    @GET("api/episode")
    suspend fun getEpisodes(@Query("page") page: Int): GetEpisodesResponse

    @GET("api/episode/{id}")
    suspend fun getEpisodeById(@Path("id") id: Int): RemoteEpisode
}