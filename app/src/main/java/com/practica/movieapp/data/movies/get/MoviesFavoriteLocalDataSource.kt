package com.practica.movieapp.data.movies.get

import com.practica.movieapp.data.movies.MovieFavorite
import com.practica.movieapp.data.movies.db.MovieFavoriteDao
import com.practica.movieapp.database.Database

class MoviesFavoriteLocalDataSource(database: Database) {
    private val movieFavoriteDao: MovieFavoriteDao = database.movieAppDb.movieFavoriteDao()

    fun getAll() = movieFavoriteDao.getAll()
    fun save(movieFavorite: MovieFavorite) = movieFavoriteDao.save(movieFavorite)
    fun saveAll(moviesFavorite: List<MovieFavorite>) = movieFavoriteDao.saveAll(moviesFavorite)
    fun delete(movieFavorite: MovieFavorite) = movieFavoriteDao.delete(movieFavorite)
    fun deleteAll() = movieFavoriteDao.deleteAll()
    fun deleteAll(moviesFavorite: List<MovieFavorite>) = movieFavoriteDao.deleteAll(moviesFavorite)
    fun replaceAll(moviesFavorite: List<MovieFavorite>) = movieFavoriteDao.replaceAll(moviesFavorite)
    fun size() = movieFavoriteDao.size()
}
