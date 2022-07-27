package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.MoviesPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("with_cast") withCast: String,
        @Query("with_genres") withGenres: String
    ): Call<MoviesPageResponse>
}