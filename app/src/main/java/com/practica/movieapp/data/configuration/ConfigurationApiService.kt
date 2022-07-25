package com.practica.movieapp.data.configuration

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigurationApiService {
    @GET("configuration")
    fun getActors(
        @Query("api_key") apiKey: String,
    ): Call<ConfigurationResponse>
}