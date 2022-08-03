package com.practica.movieapp.data.genres.get

import com.practica.movieapp.data.genres.GenresListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GenresApiService {
    @GET("genre/movie/list")
    fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String
    ): Call<GenresListResponse>
}