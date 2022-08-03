package com.practica.movieapp.data.genres.get

import com.practica.movieapp.data.genres.Genre
import com.practica.movieapp.data.genres.GenreMapper
import com.practica.movieapp.network.CallExtensions.executeAndDeliver
import com.practica.movieapp.utils.Constants
import retrofit2.Retrofit

class GenresRemoteDataSource(retrofit: Retrofit) {
    private val apiService: GenresApiService = retrofit.create(GenresApiService::class.java)
    private val genreMapper: GenreMapper = GenreMapper()

    fun getGenres(): List<Genre> {
        return apiService.getGenres(Constants.API_KEY, Constants.LANGUAGE)
            .executeAndDeliver()
            .genres
            .map { genreMapper.map(it) }
    }
}