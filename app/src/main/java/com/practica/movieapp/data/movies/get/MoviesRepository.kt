package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.database.Database
import com.practica.movieapp.network.ApiClient

class MoviesRepository private constructor() {
    companion object {
        val instance = MoviesRepository()
    }

    private val moviesRemoteDataSource = MoviesRemoteDataSource(ApiClient.instance.retrofit!!)
    private val moviesLocalDataSource = MoviesLocalDataSource(Database.instance)

    fun getAllRemoteMovies(page: Int, actorIds: Array<Int>, genreIds: Array<Int>) =
        moviesRemoteDataSource.getMoviesFromDiscovery(page, actorIds, genreIds)

    fun getRemoteMoviesBySearchQuery(page: Int, query: String) =
        moviesRemoteDataSource.getMoviesFromSearchQuery(page, query)

    fun getRemoteMovieDetailsById(id: Int) = moviesRemoteDataSource.getMovieDetailsById(id)

    fun getAllLocalMovies() = moviesLocalDataSource.getAll()
    fun saveLocal(movie: Movie) = moviesLocalDataSource.save(movie)
    fun saveAllLocal(movies: List<Movie>) = moviesLocalDataSource.saveAll(movies)
    fun deleteLocal(movie: Movie) = moviesLocalDataSource.delete(movie)
    fun deleteAllLocal(movies: List<Movie>) = moviesLocalDataSource.deleteAll(movies)
    fun deleteAllLocal() = moviesLocalDataSource.deleteAll()
    fun getCount() = moviesLocalDataSource.size()
    fun replaceAllLocal(movies: List<Movie>) = moviesLocalDataSource.replaceAll(movies)
    fun getFavorite() = moviesLocalDataSource.getFavorite()
    fun getWatched() = moviesLocalDataSource.getWatched()
}