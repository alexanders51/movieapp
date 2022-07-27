package com.practica.movieapp.data.movies.get

import com.practica.movieapp.network.ApiClient

class MoviesRepository private constructor() {
    companion object {
        val instance = MoviesRepository()
    }

    private val moviesRemoteDataSource = MoviesRemoteDataSource(ApiClient.instance.retrofit!!)

    fun getMovies(page: Int, actorIds: Array<Int>, genreIds: Array<Int>) = moviesRemoteDataSource.getMovies(page, actorIds, genreIds)
}