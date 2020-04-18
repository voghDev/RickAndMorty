package com.juangomez.remote.services.characters

import com.juangomez.remote.models.RemoteCharacter
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPIService {

    @GET("/character/{ids}")
    suspend fun getEpisodeById(@Path("ids") ids: List<Int>): List<RemoteCharacter>
}