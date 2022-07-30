package com.practica.movieapp.data.movies.db

import androidx.room.*
import com.practica.movieapp.data.movies.MovieFavorite

@Dao
interface MovieFavoriteDao {
    @Query("SELECT * FROM movie_favorite")
    fun getAll(): List<MovieFavorite>

    @Insert
    fun save(movieFavorite: MovieFavorite)

    @Insert
    fun saveAll(moviesFavorite: List<MovieFavorite>)

    @Delete
    fun delete(movieFavorite: MovieFavorite)

    @Delete
    fun deleteAll(moviesFavorite: List<MovieFavorite>)

    @Query("DELETE FROM movie_favorite")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie_favorite")
    fun size(): Int

    @Transaction
    fun replaceAll(movieFavorites: List<MovieFavorite>) {
        deleteAll()
        saveAll(movieFavorites)
    }
}
