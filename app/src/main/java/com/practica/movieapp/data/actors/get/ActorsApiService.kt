package com.practica.movieapp.data.actors.get

import com.practica.movieapp.data.actors.ActorsPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorsApiService {
    @GET("person/popular")
    fun getActors(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: String
    ): Call<ActorsPageResponse>
}