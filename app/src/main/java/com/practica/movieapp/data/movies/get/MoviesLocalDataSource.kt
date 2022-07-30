package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.Movie
import com.practica.movieapp.data.movies.db.MovieDao
import com.practica.movieapp.database.Database

class MoviesLocalDataSource(database: Database) {
    private val movieDao: MovieDao = database.movieAppDb.movieDao()

    fun getAll() = movieDao.getAll()
    fun save(movie: Movie) = movieDao.save(movie)
    fun saveAll(movies: List<Movie>) = movieDao.saveAll(movies)
    fun delete(movie: Movie) = movieDao.delete(movie)
    fun deleteAll() = movieDao.deleteAll()
    fun deleteAll(movies: List<Movie>) = movieDao.deleteAll(movies)
    fun replaceAll(movies: List<Movie>) = movieDao.replaceAll(movies)
    fun size() = movieDao.size()
}
