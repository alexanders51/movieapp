package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.MovieWatched
import com.practica.movieapp.data.movies.db.MovieWatchedDao
import com.practica.movieapp.database.Database

class MoviesWatchedLocalDataSource(database: Database) {
    private val movieWatchedDao: MovieWatchedDao = database.movieAppDb.movieWatchedDao()

    fun getAll() = movieWatchedDao.getAll()
    fun save(movieWatched: MovieWatched) = movieWatchedDao.save(movieWatched)
    fun saveAll(moviesWatched: List<MovieWatched>) = movieWatchedDao.saveAll(moviesWatched)
    fun delete(movieWatched: MovieWatched) = movieWatchedDao.delete(movieWatched)
    fun deleteAll() = movieWatchedDao.deleteAll()
    fun deleteAll(moviesWatched: List<MovieWatched>) = movieWatchedDao.deleteAll(moviesWatched)
    fun replaceAll(moviesWatched: List<MovieWatched>) = movieWatchedDao.replaceAll(moviesWatched)
    fun size() = movieWatchedDao.size()
}
