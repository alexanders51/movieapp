package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.MovieDetailsResponse
import com.practica.movieapp.data.movies.MoviesPageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {
    @GET("discover/movie")
    fun getMoviesFromDiscoveryApi(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("with_cast") withCast: String,
        @Query("with_genres") withGenres: String
    ): Call<MoviesPageResponse>

    @GET("search/movie")
    fun getMoviesFromSearchApi(
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("page") page: String,
        @Query("query") query: String
    ): Call<MoviesPageResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetailsById(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("language") lang: String,
        @Query("append_to_response") appendToResponse: String
    ): Call<MovieDetailsResponse>
}