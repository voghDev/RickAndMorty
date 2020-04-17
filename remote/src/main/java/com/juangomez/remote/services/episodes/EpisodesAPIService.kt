package com.juangomez.remote.services.episodes

import com.juangomez.remote.models.RemoteEpisode
import com.juangomez.remote.responses.GetEpisodesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesAPIService {

    @GET("/episode")
    suspend fun getEpisodes(@Query("page") page: Int): GetEpisodesResponse

    @GET("/episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): RemoteEpisode
}