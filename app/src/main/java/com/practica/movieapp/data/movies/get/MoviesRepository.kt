package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.database.Database
import com.practica.movieapp.network.ApiClient

class MoviesRepository private constructor() {
    companion object {
        val instance = MoviesRepository()
    }

    private val moviesRds = MoviesRemoteDataSource(ApiClient.instance.retrofit!!)
    private val moviesLds = MoviesLocalDataSource(Database.instance)

    fun getAllRemoteMovies(page: Int, actorIds: Array<Int>, genreIds: Array<Int>) =
        moviesRds.retrieveDiscovery(page, actorIds, genreIds)

    fun queryMovies(page: Int, query: String) = moviesRds.retrieveSearch(page, query)

    fun getAllLocalMovies() = moviesLds.getAll()

    fun saveLocal(movie: Movie) = moviesLds.save(movie)
    fun saveAllLocal(movies: List<Movie>) = moviesLds.saveAll(movies)
    fun deleteLocal(movie: Movie) = moviesLds.delete(movie)
    fun deleteAllLocal(movies: List<Movie>) = moviesLds.deleteAll(movies)
    fun deleteAllLocal() = moviesLds.deleteAll()
    fun getCount() = moviesLds.size()
    fun replaceAllLocal(movies: List<Movie>) = moviesLds.replaceAll(movies)
}