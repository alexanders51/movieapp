package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.MovieFavorite
import com.practica.movieapp.data.movies.MovieWatched
import com.practica.movieapp.database.Database
import com.practica.movieapp.network.ApiClient

class MoviesRepository private constructor() {
    companion object {
        val instance = MoviesRepository()
    }

    private val moviesRds = MoviesRemoteDataSource(ApiClient.instance.retrofit!!)
    private val moviesLds = MoviesLocalDataSource(Database.instance)
    private val moviesFavoriteLds = MoviesFavoriteLocalDataSource(Database.instance)
    private val moviesWatchedLds = MoviesWatchedLocalDataSource(Database.instance)

    fun getAllRemoteMovies(page: Int, actorIds: Array<Int>, genreIds: Array<Int>) = moviesRds.getMovies(page, actorIds, genreIds)

    fun getAllLocalMovies() = moviesLds.getAll()
    fun getAllLocalFavoriteMovies() = moviesFavoriteLds.getAll()
    fun getAllLocalWatchedMovies() = moviesWatchedLds.getAll()

    fun saveLocal(movie: Movie) = moviesLds.save(movie)
    fun saveAllLocal(movies: List<Movie>) = moviesLds.saveAll(movies)
    fun deleteLocal(movie: Movie) = moviesLds.delete(movie)
    fun deleteAllLocal(movies: List<Movie>) = moviesLds.deleteAll(movies)
    fun deleteAllLocal() = moviesLds.deleteAll()
    fun getCount() = moviesLds.size()
    fun replaceAllLocal(movies: List<Movie>) = moviesLds.replaceAll(movies)

    fun saveLocalFavorite(movieFavorite: MovieFavorite) = moviesFavoriteLds.save(movieFavorite)
    fun saveAllLocalFavorite(moviesFavorite: List<MovieFavorite>) = moviesFavoriteLds.saveAll(moviesFavorite)
    fun deleteLocalFavorite(movieFavorite: MovieFavorite) = moviesFavoriteLds.delete(movieFavorite)
    fun deleteAllLocalFavorite(moviesFavorite: List<MovieFavorite>) = moviesFavoriteLds.deleteAll(moviesFavorite)
    fun deleteAllLocalFavorite() = moviesFavoriteLds.deleteAll()
    fun getCountFavorite() = moviesFavoriteLds.size()
    fun replaceAllLocalFavorite(moviesFavorite: List<MovieFavorite>) = moviesFavoriteLds.replaceAll(moviesFavorite)

    fun saveLocalWatched(movieWatched: MovieWatched) = moviesWatchedLds.save(movieWatched)
    fun saveAllLocalWatched(moviesWatched: List<MovieWatched>) = moviesWatchedLds.saveAll(moviesWatched)
    fun deleteLocalWatched(movieWatched: MovieWatched) = moviesWatchedLds.delete(movieWatched)
    fun deleteAllLocalWatched(moviesWatched: List<MovieWatched>) = moviesWatchedLds.deleteAll(moviesWatched)
    fun deleteAllLocalWatched() = moviesWatchedLds.deleteAll()
    fun getCountWatched() = moviesWatchedLds.size()
    fun replaceAllLocalWatched(moviesWatched: List<MovieWatched>) = moviesWatchedLds.replaceAll(moviesWatched)
}